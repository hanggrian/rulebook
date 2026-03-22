from re import Match, Pattern, compile as re

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class DuplicateSpaceChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#duplicate-space"""
    ID: str = 'duplicate-space'
    _MSG: str = 'duplicate.space'

    _DUPLICATE_REGEX: Pattern = re(r'(?<=\S)(?<!\*)[ \t]{2,}')
    _STRING_REGEX: Pattern = re(
        r'R"([^(]*)\(.*?\)\1"' +
        r'|"(?:[^"\\]|\\.)*"',
    )

    def check_file(self, token: Token, content: str) -> None:
        for line_number, line in enumerate(content.splitlines(), start=1):
            # checks for violation
            match: Match[str] | None = self._DUPLICATE_REGEX.search(self._mask_strings(line))
            if match is None:
                continue
            self.report_error(
                token,
                _Messages.get(self._MSG),
                line_number,
                match.start() + 1,
            )

    def _mask_strings(self, line: str) -> str:
        return self._STRING_REGEX.sub(lambda m: '_' * len(m.group()), line)
