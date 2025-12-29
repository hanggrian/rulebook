from astroid.nodes import NodeNG, Assign, AssignName, ClassDef, FunctionDef
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages
from rulebook_pylint.nodes import _has_decorator

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class MemberOrderChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#member-order"""
    MSG: str = 'member-order'

    name: str = 'member-order'
    msgs: dict[str, MessageDefinitionTuple] = _Messages.of(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # in Python, static members have are annotated
        last_child: FunctionDef | None = None
        for child in node.values():
            # checks for violation
            if last_child and \
                self._get_member_position(last_child) > self._get_member_position(child):
                self.add_message(
                    self.MSG,
                    node=child,
                    args=(
                        self._get_member_argument(child),
                        self._get_member_argument(last_child),
                    ),
                )

            last_child = child

    @staticmethod
    def _get_member_position(node: NodeNG) -> int:
        if isinstance(node, Assign):
            if _has_decorator(node, 'staticmethod'):
                return 4
            return 1
        if isinstance(node, AssignName):
            return 1
        if isinstance(node, FunctionDef):
            if _has_decorator(node, 'staticmethod'):
                return 4
            return 2 if node.name == '__init__' else 3
        return -1

    @staticmethod
    def _get_member_argument(node: NodeNG) -> str:
        if isinstance(node, Assign):
            if _has_decorator(node, 'staticmethod'):
                return 'static member'
            return 'property'
        if isinstance(node, AssignName):
            return 'property'
        if isinstance(node, FunctionDef):
            if _has_decorator(node, 'staticmethod'):
                return 'static member'
            return 'constructor' if node.name == '__init__' else 'function'
        return ''


def register(linter: 'PyLinter') -> None:
    linter.register_checker(MemberOrderChecker(linter))
