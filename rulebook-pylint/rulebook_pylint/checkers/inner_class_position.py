from astroid.nodes import NodeNG, ClassDef, FunctionDef
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class InnerClassPositionChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#inner-class-position"""
    MSG: str = 'inner-class-position'

    name: str = 'inner-class-position'
    msgs: dict[str, MessageDefinitionTuple] = _Messages.of(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # consider only inner class
        if node.parent and not isinstance(node.parent, ClassDef):
            return

        current: NodeNG = node
        while current:
            # checks for violation
            if isinstance(current, FunctionDef):
                self.add_message(self.MSG, node=node)
                return

            current = current.next_sibling()


def register(linter: 'PyLinter') -> None:
    linter.register_checker(InnerClassPositionChecker(linter))
