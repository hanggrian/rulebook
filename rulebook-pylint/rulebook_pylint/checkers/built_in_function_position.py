from astroid.nodes import NodeNG, FunctionDef
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages
from rulebook_pylint.nodes import _has_decorator

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class BuiltInFunctionPositionChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#built-in-function-position"""
    MSG: str = 'built.in.function.position'

    SPECIAL_FUNCTIONS = (
        '__str__',
        '__hash__',
        '__eq__',
        '__new__',
        '__del__',
        '__repr__',
        '__bytes__',
        '__format__',
        '__lt__',
        '__le__',
        '__ne__',
        '__gt__',
        '__ge__',
        '__bool__',
    )

    name: str = 'built-in-function-position'
    msgs: dict[str, MessageDefinitionTuple] = _Messages.of(MSG)

    def visit_functiondef(self, node: FunctionDef) -> None:
        # target special function
        if node.name not in self.SPECIAL_FUNCTIONS:
            return

        current: NodeNG = node
        while current:
            # checks for violation
            if isinstance(current, FunctionDef) and \
                not _has_decorator(current, 'staticmethod') and \
                current.name not in self.SPECIAL_FUNCTIONS:
                self.add_message(self.MSG, node=node, args=node.name)
                return

            current = current.next_sibling()


def register(linter: 'PyLinter') -> None:
    linter.register_checker(BuiltInFunctionPositionChecker(linter))
