from typing import TYPE_CHECKING

from astroid import NodeNG, Compare, If, Assign
from pylint.checkers import BaseChecker
from pylint.typing import MessageDefinitionTuple

from .internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ObjectStructuralComparisonChecker(BaseChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#object-structural-comparison
    """
    MSG_EQ: str = 'object-structural-comparison-eq'
    MSG_NEQ: str = 'object-structural-comparison-neq'

    name: str = 'object-structural-comparison'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG_EQ, MSG_NEQ)

    def visit_compare(self, node: Compare) -> None:
        if isinstance(node, If):
            self._process(node.test)
        elif isinstance(node, Assign):
            self._process(node.value)

    def _process(self, node: NodeNG):
        # skip no comparison and multiple operators
        if not isinstance(node, Compare):
            return
        if len(node.ops) > 1:
            return
        ops = node.ops[0]

        # find qualifying operator
        operator: str = ops[0]
        if operator == 'is not':
            msg: str = ObjectStructuralComparisonChecker.MSG_NEQ
        elif operator == 'is':
            msg: str = ObjectStructuralComparisonChecker.MSG_EQ
        else:
            return

        # checks for violation
        left: NodeNG = node.left
        right: NodeNG = ops[1]
        if left.as_string() == 'None' or right.as_string() == 'None':
            return
        self.add_message(msg, node=node)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(ObjectStructuralComparisonChecker(linter))
