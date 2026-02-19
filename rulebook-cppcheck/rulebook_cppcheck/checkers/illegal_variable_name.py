from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.options import ILLEGAL_VARIABLE_NAMES_OPTION

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class IllegalVariableNameChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#illegal-variable-name"""
    ID: str = 'illegal-variable-name'
    _MSG: str = 'illegal.variable.name'
    ARGS: list[str] = [ILLEGAL_VARIABLE_NAMES_OPTION]

    def __init__(self) -> None:
        super().__init__()
        self._illegal_variable_names: set[str] = \
            {'integer', 'string', 'integers', 'strings'}
        self._reported_variables: set[str] = set()

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self._illegal_variable_names = \
            set(args[ILLEGAL_VARIABLE_NAMES_OPTION].split(','))

    def process_tokens(self, tokens: list[Token]) -> None:
        # checks for violation
        for token in [t for t in tokens if t.variable]:
            if token.str not in self._illegal_variable_names:
                continue
            token_id: str = token.variableId
            if token_id in self._reported_variables:
                continue
            self._reported_variables.add(token_id)
            self.report_error(token, _Messages.get(self._MSG))
