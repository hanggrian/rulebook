from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class DuplicateBlankLineChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line"""
    ID: str = 'duplicate-blank-line'
    MSG: str = 'duplicate.blank.line'

    @override
    def check_file(self, token: Token, content: str) -> None:
        lines: list[str] = content.splitlines()
        for i in range(1, len(lines)):
            if lines[i].strip() or lines[i - 1].strip():
                continue
            self.report_error(token, _Messages.get(self.MSG), line=i + 1)
