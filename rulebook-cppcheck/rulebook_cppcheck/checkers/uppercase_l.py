from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class UppercaseLChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#uppercase-l"""
    ID: str = 'uppercase-l'
    _MSG: str = 'uppercase.l'

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        # checks for violation
        for token in [t for t in tokens if t.isNumber]:
            if 'l' not in token.str:
                return
            self.report_error(token, _Messages.get(self._MSG))
