from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class FinalNewlineChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#final-newline"""
    MSG: str = 'final.newline'

    id: str = 'final-newline'

    @override
    def check_file(self, token: Token, content: str) -> None:
        if not content.endswith('\n'):
            self.report_error(token, _Messages.get(self.MSG))
