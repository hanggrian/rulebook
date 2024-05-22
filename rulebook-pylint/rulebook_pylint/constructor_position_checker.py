from typing import TYPE_CHECKING

from astroid import NodeNG, FunctionDef, Assign, AssignName
from pylint.checkers import BaseChecker
from pylint.typing import MessageDefinitionTuple

from .internals import Messages, get_assignname

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class ConstructorPositionChecker(BaseChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#constructor-position
    """
    MSG_PROPERTIES: str = 'constructor-position-properties'
    MSG_METHODS: str = 'constructor-position-methods'

    name: str = 'constructor-position'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG_PROPERTIES, MSG_METHODS)

    def visit_functiondef(self, node: FunctionDef) -> None:
        # there is only one constructor in Python, target method directly
        if node.name != '__init__':
            return None

        # checks for violation
        next_node: NodeNG = node.next_sibling()
        while next_node:
            if isinstance(next_node, Assign):
                target: AssignName | None = get_assignname(next_node)
                if not target:
                    continue
                self.add_message(ConstructorPositionChecker.MSG_PROPERTIES, node=target)
            next_node: NodeNG = next_node.next_sibling()
        prev_node: NodeNG = node.previous_sibling()
        while prev_node:
            if isinstance(prev_node, FunctionDef):
                self.add_message(ConstructorPositionChecker.MSG_METHODS, node=prev_node)
            prev_node: NodeNG = prev_node.previous_sibling()
        return None

def register(linter: 'PyLinter') -> None:
    linter.register_checker(ConstructorPositionChecker(linter))
