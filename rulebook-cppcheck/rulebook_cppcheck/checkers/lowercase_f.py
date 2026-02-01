from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class LowercaseFChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#lowercase-f"""
    ID: str = 'lowercase-f'
    MSG: str = 'lowercase.f'

    @override
    def process_token(self, token: Token) -> None:
        # checks for violation
        if not token.isFloat:
            return
        if 'F' in token.str:
            self.report_error(token, _Messages.get(self.MSG))
