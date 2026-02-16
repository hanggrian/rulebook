from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class FinalNewlineChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#final-newline"""
    ID: str = 'final-newline'
    _MSG: str = 'final.newline'

    @override
    def check_file(self, token: Token, content: str) -> None:
        # ignore empty file
        if len(content.strip()) == 0:
            return

        # checks for violation
        if content.endswith('\n'):
            return
        self.report_error(token, _Messages.get(self._MSG))
