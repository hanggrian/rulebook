from typing import TYPE_CHECKING

from astroid import NodeNG, If, For, While, FunctionDef
from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import is_multiline

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class IfElseFlatteningChecker(Checker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#if-else-flattening
    """
    MSG_INVERT: str = 'if-else-flattening-invert'
    MSG_LIFT: str = 'if-else-flattening-lift'

    name: str = 'if-else-flattening'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_INVERT, MSG_LIFT)

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
        else2: list[NodeNG] = if2.orelse

        if len(else2) > 0:
            else_first_child: NodeNG = else2[0]
            if self._else_has_if(else2):
                return
            if self._has_multiple_lines(else2):
                self.add_message(self.MSG_LIFT, node=else_first_child)
            return
        if self._has_multiple_lines(if2.body):
            self.add_message(self.MSG_INVERT, node=if2)

    @staticmethod
    def _else_has_if(nodes: list[NodeNG]) -> bool:
        for node in nodes:
            if isinstance(node, If):
                return True
        return False

    @staticmethod
    def _has_multiple_lines(nodes: list[NodeNG]) -> bool:
        length: int = len(nodes)
        if length == 1:
            return is_multiline(nodes[0])
        return length > 1


def register(linter: 'PyLinter') -> None:
    linter.register_checker(IfElseFlatteningChecker(linter))
