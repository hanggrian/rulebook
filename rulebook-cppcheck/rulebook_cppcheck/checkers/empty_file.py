from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class EmptyFileChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#empty-file"""
    ID: str = 'empty-file'
    _MSG: str = 'empty.file'

    @override
    def check_file(self, token: Token, content: str) -> None:
        # checks for violation
        if len(content.strip()) > 0:
            return
        self.report_error(token, _Messages.get(self._MSG))
