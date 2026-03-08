from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class LowercaseHexadecimalChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#lowercase-hexadecimal"""
    ID: str = 'lowercase-hexadecimal'
    _MSG: str = 'lowercase.hexadecimal'

    def process_tokens(self, tokens: list[Token]) -> None:
        # checks for violation
        for token in [t for t in tokens if t.isNumber]:
            value: str = token.str
            if not value.lower().startswith('0x'):
                return
            value_replacement: str = value.lower()
            if value == value_replacement:
                return
            self.report_error(token, _Messages.get(self._MSG, value_replacement))
