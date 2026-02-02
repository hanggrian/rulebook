from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class IllegalVariableNameChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#illegal-variable-name"""
    ID: str = 'illegal-variable-name'
    MSG: str = 'illegal.variable.name'
    ARG_ILLEGAL_VARIABLE_NAMES: str = 'illegal-variable-names'
    ARGS = [ARG_ILLEGAL_VARIABLE_NAMES]

    def __init__(self):
        super().__init__()
        self._illegal_variable_names: set[str] = \
            {'integer', 'string', 'integers', 'strings'}

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self._illegal_variable_names = \
            set(args[self.ARG_ILLEGAL_VARIABLE_NAMES].split(','))

    def process_token(self, token: Token) -> None:
        if token.variable and token is token.variable.nameToken:
            self._process(token)
        if token.function and token is token.function.tokenDef:
            self._process(token)

    def _process(self, token: Token) -> None:
        # checks for violation
        if token.str not in self._illegal_variable_names:
            return
        self.report_error(token, _Messages.get(self.MSG))
