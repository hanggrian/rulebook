from __future__ import annotations

from astroid.nodes import Assign, AssignName, ClassDef, FunctionDef
from pylint.typing import Options, TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import Messages
from rulebook_pylint.nodes import has_decorator
from rulebook_pylint.options import MEMBER_ORDER_OPTION

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class MemberOrderChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#member-order"""
    _MSG: str = 'member.order'

    name: str = 'member-order'
    msgs: dict[str, tuple[str, str, str]] = Messages.of(_MSG)
    options: Options = (
        MEMBER_ORDER_OPTION,
    )

    _member_order: list[str]
    _property_position: int
    _constructor_position: int
    _function_position: int
    _static_position: int

    def open(self) -> None:
        self._member_order = self.linter.config.rulebook_member_order
        self._property_position = self._member_order.index('property')
        self._constructor_position = self._member_order.index('constructor')
        self._function_position = self._member_order.index('function')
        self._static_position = self._member_order.index('static')

    def visit_classdef(self, node: ClassDef) -> None:
        # in Python, static members have are annotated
        last_child: Assign | AssignName | FunctionDef | None = None
        for child in [
            n for n in node.values()
            if isinstance(n, (Assign, AssignName, FunctionDef))
        ]:
            # checks for violation
            if last_child is not None and \
                self._get_member_position(last_child) > self._get_member_position(child):
                self.add_message(
                    self._MSG,
                    node=child,
                    args=(
                        self._get_member_argument(child),
                        self._get_member_argument(last_child),
                    ),
                )

            last_child = child

    def _get_member_position(self, node: Assign | AssignName | FunctionDef) -> int:
        if isinstance(node, Assign):
            return self._property_position
        if isinstance(node, AssignName):
            return self._property_position
        if has_decorator(node, 'staticmethod'):
            return self._static_position
        return self._constructor_position \
            if node.name == '__init__' \
            else self._function_position

    @staticmethod
    def _get_member_argument(node: Assign | AssignName | FunctionDef) -> str:
        if isinstance(node, Assign):
            return 'property'
        if isinstance(node, AssignName):
            return 'property'
        if has_decorator(node, 'staticmethod'):
            return 'static member'
        return 'constructor' \
            if node.name == '__init__' \
            else 'function'


def register(linter: PyLinter) -> None:
    linter.register_checker(MemberOrderChecker(linter))
