from typing import TYPE_CHECKING

from astroid import NodeNG, FunctionDef, If
from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class IfFlatteningChecker(Checker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#if-flattening
    """
    MSG: str = 'if-flattening'

    name: str = 'if-flattening'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def visit_functiondef(self, node: FunctionDef) -> None:
        # only proceed on one if and no else
        if2: NodeNG = next(iter(node.body), None)
        if if2 is None or not isinstance(if2, If) or len(if2.orelse) > 0:
            return None

        # report 2 lines content
        if len(if2.body) < 2:
            return None
        self.add_message(self.MSG, node=if2)
        return None


def register(linter: 'PyLinter') -> None:
    linter.register_checker(IfFlatteningChecker(linter))
