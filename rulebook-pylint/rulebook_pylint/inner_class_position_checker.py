from typing import TYPE_CHECKING

from astroid import NodeNG, ClassDef, FunctionDef
from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class InnerClassPositionChecker(Checker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#inner-class-organization
    """
    MSG: str = 'inner-class-position'

    name: str = 'inner-class-position'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # consider only inner class
        if node.parent is not None and not isinstance(node.parent, ClassDef):
            return

        current: NodeNG = node
        while current is not None:
            # checks for violation
            if isinstance(current, FunctionDef):
                self.add_message(self.MSG, node=node)
                return

            current = current.next_sibling()


def register(linter: 'PyLinter') -> None:
    linter.register_checker(InnerClassPositionChecker(linter))
