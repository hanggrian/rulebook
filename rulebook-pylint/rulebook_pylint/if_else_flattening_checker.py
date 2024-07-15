from typing import TYPE_CHECKING

from astroid import NodeNG, If, For, While, FunctionDef
from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class IfElseFlatteningChecker(Checker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#if-else-flattening
    """
    MSG_INVERT: str = 'if-else-flattening-invert'
    MSG_LIFT: str = 'if-else-flattening-lift'

    name: str = 'if-else-flattening'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_INVERT, MSG_LIFT)

    def visit_if(self, node: If) -> None:
        self._process(node.body)

    def visit_for(self, node: For) -> None:
        self._process(node.body)

    def visit_while(self, node: While) -> None:
        self._process(node.body)

    def visit_functiondef(self, node: FunctionDef) -> None:
        self._process(node.body)

    def _process(self, body: list[NodeNG]) -> None:
        # get last if
        if2: If | None = None
        children: list[NodeNG] = body.copy()
        children.reverse()
        for child in children:
            if isinstance(child, If):
                if2 = child
                break
            return
        if if2 is None:
            return

        # checks for violation
        else2 = if2.orelse
        if len(else2) > 0:
            if isinstance(else2[0], If):
                return
            self.add_message(self.MSG_LIFT, node=else2[0])
            return
        if len(if2.body) < 2:
            return
        self.add_message(self.MSG_INVERT, node=if2)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(IfElseFlatteningChecker(linter))
