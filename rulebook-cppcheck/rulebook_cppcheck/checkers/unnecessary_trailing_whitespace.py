from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class UnnecessaryTrailingWhitespaceChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#unnecessary-trailing-whitespace"""
    ID: str = 'unnecessary-trailing-whitespace'
    _MSG: str = 'unnecessary.trailing.whitespace'

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
