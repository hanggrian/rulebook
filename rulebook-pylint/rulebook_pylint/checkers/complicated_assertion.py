from __future__ import annotations

from astroid.nodes import Attribute, Call, ClassDef, Compare, Const, Expr, FunctionDef, Name, NodeNG
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ComplicatedAssertionChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#complicated-assertion"""
    _MSG: str = 'complicated.assertion'

    name: str = 'complicated-assertion'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)

    _BOOLEAN_ASSERTIONS = frozenset(['assertTrue', 'assertFalse'])
    _EQUALITY_ASSERTIONS = frozenset(['assertEqual', 'assertNotEqual'])

    def visit_classdef(self, node: ClassDef) -> None:
        # find built-in tests
        if not any(isinstance(b, Name) and b.name == 'TestCase' for b in node.bases):
            return

        for method in [m for m in node.methods() if isinstance(m, FunctionDef)]:
            for expr in [b for b in method.body if isinstance(b, Expr)]:
                # checks for violation
                call: NodeNG = expr.value
                if not isinstance(call, Call) or \
                    not isinstance(call.func, Attribute):
                    continue
                name: str = call.func.attrname
                if not call.args:
                    continue
                call_replacement: str
                if name in self._BOOLEAN_ASSERTIONS:
                    arg: NodeNG = call.args[0]
                    if not isinstance(arg, Compare):
                        continue
                    op: str = arg.ops[0][0]
                    if op == '==':
                        call_replacement = 'assertEqual'
                    elif op == '!=':
                        call_replacement = 'assertNotEqual'
                    elif op == 'is':
                        call_replacement = 'assertIs'
                    elif op == 'is not':
                        call_replacement = 'assertIsNot'
                    else:
                        continue
                elif name in self._EQUALITY_ASSERTIONS:
                    args: list[NodeNG] = call.args
                    if any(self._is_const(arg, True) for arg in args):
                        call_replacement = 'assertTrue'
                    elif any(self._is_const(arg, False) for arg in args):
                        call_replacement = 'assertFalse'
                    elif any(self._is_const(arg, None) for arg in args):
                        call_replacement = 'assertIsNone'
                    else:
                        continue
                else:
                    continue
                self.add_message(self._MSG, node=call.func, args=call_replacement)

    @staticmethod
    def _is_const(node: NodeNG, value: object) -> bool:
        return isinstance(node, Const) and \
            node.value is value


def register(linter: PyLinter) -> None:
    linter.register_checker(ComplicatedAssertionChecker(linter))
