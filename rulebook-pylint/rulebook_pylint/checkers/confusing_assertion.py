from __future__ import annotations

from astroid.nodes import Attribute, Call, ClassDef, Expr, FunctionDef, Name, NodeNG, UnaryOp
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.collections import two_way_dict
from rulebook_pylint.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ConfusingAssertionChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#confusing-assertion"""
    _MSG: str = 'confusing.assertion'

    _ASSERT_CALLS: dict[str, str] = \
        two_way_dict(('assertTrue', 'assertFalse'))

    name: str = 'confusing-assertion'
    msgs: dict[str, tuple[str, str, str]] = Messages.of(_MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # find built-in tests
        if not any(isinstance(b, Name) and b.name == 'TestCase' for b in node.bases):
            return

        for method in [m for m in node.methods() if isinstance(m, FunctionDef)]:
            for expr in [b for b in method.body if isinstance(b, Expr)]:
                # find inverted assert function
                call: NodeNG = expr.value
                if not isinstance(call, Call) or \
                    not isinstance(call.func, Attribute):
                    continue
                call_replacement: str | None = self._ASSERT_CALLS.get(call.func.attrname, None)
                if call_replacement is None:
                    continue

                # checks for violation
                if not call.args:
                    continue
                arg: NodeNG = call.args[0]
                if not isinstance(arg, UnaryOp) or \
                    arg.op != 'not':
                    continue
                self.add_message(self._MSG, node=call.func, args=call_replacement)


def register(linter: PyLinter) -> None:
    linter.register_checker(ConfusingAssertionChecker(linter))
