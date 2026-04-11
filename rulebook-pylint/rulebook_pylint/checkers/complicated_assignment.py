from __future__ import annotations

from astroid.nodes import Assign, AssignName, BinOp, Name, NodeNG
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ComplicatedAssignmentChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#complicated-assignment"""
    _MSG: str = 'complicated.assignment'

    _SHORTHAND_OPERATIONS: frozenset[str] = frozenset(['+', '-', '*', '/', '%'])

    name: str = 'complicated-assignment'
    msgs: dict[str, tuple[str, str, str]] = Messages.of(_MSG)

    def visit_assign(self, node: Assign) -> None:
        # skip destructuring
        if len(node.targets) != 1:
            return

        # checks for violation
        identifier: NodeNG = node.targets[0]
        if not isinstance(identifier, AssignName):
            return
        if not isinstance(node.value, BinOp):
            return
        bin_op: BinOp = self._deepest_bin_op(node.value)
        if bin_op.op not in self._SHORTHAND_OPERATIONS:
            return
        if not isinstance(bin_op.left, Name) or \
            bin_op.left.name != identifier.name:
            return
        self.add_message(self._MSG, node=node, args=bin_op.op + '=')

    @staticmethod
    def _deepest_bin_op(node: BinOp) -> BinOp:
        current: BinOp = node
        while isinstance(current.left, BinOp):
            current = current.left
        return current


def register(linter: PyLinter) -> None:
    linter.register_checker(ComplicatedAssignmentChecker(linter))
