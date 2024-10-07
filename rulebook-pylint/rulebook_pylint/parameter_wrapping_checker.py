from typing import TYPE_CHECKING

from astroid import NodeNG, FunctionDef, Call, AssignName
from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import is_multiline

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ParameterWrappingChecker(Checker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#parameter-wrapping
    """
    MSG_ARGUMENT: str = 'parameter-wrapping-argument'

    name: str = 'parameter-wrapping'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_ARGUMENT)

    def visit_functiondef(self, node: FunctionDef) -> None:
        # target multiline parameters
        args: list[AssignName] = node.args.args
        if len(args) == 0:
            return
        if args[0].lineno == args[len(args) - 1].end_lineno:
            return

        # checks for violation
        self._process(args)

    def visit_call(self, node: Call) -> None:
        # target multiline parameters
        if not is_multiline(node):
            return

        # checks for violation
        self._process(node.args)

    def _process(self, parameters: list[NodeNG]) -> None:
        parameter: NodeNG
        for i, parameter in enumerate(parameters):
            # skip first
            if i == 0:
                continue

            if parameters[i - 1].lineno + 1 != parameter.lineno:
                self.add_message(self.MSG_ARGUMENT, node=parameter)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(ParameterWrappingChecker(linter))