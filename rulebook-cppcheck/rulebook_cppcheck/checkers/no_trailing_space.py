from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class NoTrailingSpaceChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#no-trailing-space"""
    ID: str = 'no-trailing-space'
    _MSG: str = 'no.trailing.space'

    def check_file(self, token: Token, content: str) -> None:
        # checks for violation
        for line_number, line in enumerate(content.splitlines(), start=1):
            if line == line.rstrip(' \t'):
                continue
            self.report_error(
                token,
                Messages.get(self._MSG),
                line_number,
                len(line.rstrip(' \t')) + 1,
            )
