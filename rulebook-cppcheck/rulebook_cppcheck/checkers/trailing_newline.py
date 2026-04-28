from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class TrailingNewlineChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#trailing-newline"""
    ID: str = 'trailing-newline'
    _MSG: str = 'trailing.newline'

    def check_file(self, token: Token, content: str) -> None:
        # checks for violation
        if not content.strip() or \
            content.endswith('\n'):
            return
        self.report_error(token, Messages.get(self._MSG))
