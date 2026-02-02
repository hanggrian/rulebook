from astroid.nodes import NodeNG, ClassDef, FunctionDef, AssignName, Assign
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class InnerClassPositionChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#inner-class-position"""
    MSG: str = 'inner.class.position'

    name: str = 'inner-class-position'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # consider only inner class
        if node.parent and not isinstance(node.parent, ClassDef):
            return

        next2: NodeNG = node
        while next2:
            next2 = next2.next_sibling()

            # checks for violation
            if isinstance(next2, (FunctionDef, Assign, AssignName)):
                self.add_message(self.MSG, node=node)
                return


def register(linter: 'PyLinter') -> None:
    linter.register_checker(InnerClassPositionChecker(linter))
