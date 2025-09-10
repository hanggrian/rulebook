from astroid import NodeNG, If, For, While, FunctionDef
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages
from rulebook_pylint.nodes import _has_jump_statement, _is_multiline

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class NestedIfElseChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#nested-if-else"""
    MSG_INVERT: str = 'nested-if-else-invert'
    MSG_LIFT: str = 'nested-if-else-lift'

    name: str = 'nested-if-else'
    msgs: dict[str, MessageDefinitionTuple] = _Messages.of(MSG_INVERT, MSG_LIFT)

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
        if not if2:
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
        if _has_jump_statement(if2):
            return
        if self._has_multiple_lines(if2.body):
            self.add_message(self.MSG_INVERT, node=if2)

    @staticmethod
    def _else_has_if(nodes: list[NodeNG]) -> bool:
        return any(isinstance(node, If) for node in nodes)

    @staticmethod
    def _has_multiple_lines(nodes: list[NodeNG]) -> bool:
        length: int = len(nodes)
        if length == 1:
            return _is_multiline(nodes[0])
        return length > 1


def register(linter: 'PyLinter') -> None:
    linter.register_checker(NestedIfElseChecker(linter))
