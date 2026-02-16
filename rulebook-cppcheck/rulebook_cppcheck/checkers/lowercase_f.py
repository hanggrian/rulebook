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
    _MSG: str = 'lowercase.f'

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        # checks for violation
        for token in [t for t in tokens if t.isFloat]:
            if 'F' not in token.str:
                continue
            self.report_error(token, _Messages.get(self._MSG))
