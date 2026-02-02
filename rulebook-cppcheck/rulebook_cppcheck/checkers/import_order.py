from re import compile as re, Pattern, Match
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class ImportOrderChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#import-order"""
    ID: str = 'import-order'
    MSG_SORT: str = 'import.order.sort'
    MSG_JOIN: str = 'import.order.join'

    INCLUDE_REGEX: Pattern = re(r'#\s*include\s*([<"])(.+?)([>"])')

    @override
    def check_file(self, token: Token, content: str) -> None:
        prev_is_quoted: bool | None = None
        prev_lineno: int | None = None
        prev_path: str | None = None

        for lineno, line in enumerate(content.splitlines(), 1):
            # distinguish between bracket and quote imports
            match: Match | None = self.INCLUDE_REGEX.search(line.strip())
            if not match:
                continue
            is_quoted: bool = match.group(1) == '"'
            path: str = match.group(2).strip()

            # checks for violation
            if prev_lineno is not None:
                if not is_quoted and prev_is_quoted:
                    self.report_error(
                        token,
                        _Messages.get(self.MSG_SORT, path, prev_path),
                        lineno,
                    )
                elif is_quoted == prev_is_quoted:
                    if path < prev_path:
                        self.report_error(
                            token,
                            _Messages.get(self.MSG_SORT, path, prev_path),
                            lineno,
                        )
                if lineno != prev_lineno + 1:
                    self.report_error(
                        token,
                        _Messages.get(self.MSG_JOIN, path),
                        lineno,
                    )

            prev_is_quoted = is_quoted
            prev_lineno = lineno
            prev_path = path
