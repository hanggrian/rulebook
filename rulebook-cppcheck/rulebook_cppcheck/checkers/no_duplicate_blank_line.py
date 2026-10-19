from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class NoDuplicateBlankLineChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#no-duplicate-blank-line"""
    ID: str = 'no-duplicate-blank-line'
    _MSG: str = 'no.duplicate.blank.line'

    def check_file(self, token: Token, content: str) -> None:
        lines: list[str] = content.splitlines()
        for i in range(1, len(lines)):
            # checks for violation
            if lines[i].strip() or \
                lines[i - 1].strip():
                continue
            self.report_error(token, Messages.get(self._MSG), i + 1)
