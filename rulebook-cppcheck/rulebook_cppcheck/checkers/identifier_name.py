from re import Pattern, sub, compile as re
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class IdentifierNameChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#identifier-name"""
    MSG: str = 'identifier.name'
    ID: str = 'identifier-name'

    SNAKE_CASE_REGEX: Pattern = re(r'(?<!^)(?=[A-Z][a-z])|(?<=[a-z])(?=[A-Z])')

    @override
    def process_token(self, token: Token) -> None:
        if token.variable and token is token.variable.nameToken:
            self._process(token)
        if token.function and token is token.function.tokenDef:
            self._process(token)

    def _process(self, token: Token) -> None:
        # checks for violation
        name: str = token.str
        if all(c.isupper() or c == '_' for c in name) or \
            not any(c.isupper() for c in name):
            return
        self.report_error(
            token,
            _Messages.get(
                self.MSG,
                sub(r'_+', '_', self.SNAKE_CASE_REGEX.sub('_', name).lower()),
            ),
        )
