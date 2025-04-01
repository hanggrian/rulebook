from astroid import ClassDef, FunctionDef
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookChecker
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import has_decorator

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class MemberOrderChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#member-order"""
    MSG: str = 'member-order'

    name: str = 'member-order'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # in Python, static members have are annotated
        last_method: FunctionDef | None = None
        for method in [m for m in node.mymethods() if not has_decorator(m, 'staticmethod')]:
            # checks for violation
            if last_method and \
                last_method.name != '__init__' and \
                method.name == '__init__':
                self.add_message(self.MSG, node=method, args=('constructor', 'function'))

            last_method = method


def register(linter: 'PyLinter') -> None:
    linter.register_checker(MemberOrderChecker(linter))
