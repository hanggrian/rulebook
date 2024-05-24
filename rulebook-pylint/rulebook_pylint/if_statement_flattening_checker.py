from typing import TYPE_CHECKING

from astroid import NodeNG, FunctionDef, If
from pylint.checkers import BaseChecker
from pylint.typing import MessageDefinitionTuple

from .internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class IfStatementFlatteningChecker(BaseChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#if-statement-flattening
    """
    MSG: str = 'if-statement-flattening'

    name: str = 'if-statement-flattening'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)

    def visit_functiondef(self, node: FunctionDef) -> None:
        # only proceed on one if and no else
        if2: NodeNG = next(iter(node.body), None)
        if if2 is None or not isinstance(if2, If) or len(if2.orelse) > 0:
            return None

        # report 2 lines content
        if len(if2.body) < 2:
            return
        self.add_message(IfStatementFlatteningChecker.MSG, node=if2)

def register(linter: 'PyLinter') -> None:
    linter.register_checker(IfStatementFlatteningChecker(linter))
