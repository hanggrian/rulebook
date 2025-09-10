from astroid import NodeNG, FunctionDef, Call
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ParameterWrapChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#parameter-wrap"""
    MSG: str = 'parameter-wrap'

    name: str = 'parameter-wrap'
    msgs: dict[str, MessageDefinitionTuple] = _Messages.of(MSG)

    def visit_functiondef(self, node: FunctionDef) -> None:
        self._process(node.args.args)

    def visit_call(self, node: Call) -> None:
        self._process(node.args)

    def _process(self, parameters: list[NodeNG]) -> None:
        # target multiline parameters
        if not parameters or \
            parameters[0].lineno == parameters[-1].end_lineno:
            return

        # checks for violation
        for i, parameter in enumerate(parameters):
            if i == 0 or \
                parameters[i - 1].end_lineno != parameter.lineno:
                continue
            self.add_message(self.MSG, node=parameter)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(ParameterWrapChecker(linter))
