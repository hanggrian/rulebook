from typing import TYPE_CHECKING

from astroid import NodeNG, Compare, If, Assign
from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class ObjectStructuralComparisonChecker(Checker):
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

    def _process(self, node: NodeNG) -> None:
        # skip no comparison and multiple operators
        if not isinstance(node, Compare) or len(node.ops) > 1:
            return None
        ops = node.ops[0]

        # find qualifying operator
        operator: str = ops[0]
        if operator == 'is not':
            msg: str = ObjectStructuralComparisonChecker.MSG_NEQ
        elif operator == 'is':
            msg: str = ObjectStructuralComparisonChecker.MSG_EQ
        else:
            return None

        # checks for violation
        left: NodeNG = node.left
        right: NodeNG = ops[1]
        if left.as_string() == 'None' or right.as_string() == 'None':
            return None
        self.add_message(msg, node=node)
        return None

def register(linter: 'PyLinter') -> None:
    linter.register_checker(ObjectStructuralComparisonChecker(linter))
