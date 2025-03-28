from astroid import NodeNG, FunctionDef, Call, AssignName
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookChecker
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import is_multiline

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ParameterWrapChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/all/#parameter-wrap"""
    MSG_ARGUMENT: str = 'parameter-wrap-argument'

    name: str = 'parameter-wrap'
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
        for i, parameter in enumerate(parameters):
            # skip first
            if i == 0:
                continue

            if parameters[i - 1].end_lineno + 1 == parameter.lineno:
                continue
            self.add_message(self.MSG_ARGUMENT, node=parameter)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(ParameterWrapChecker(linter))
