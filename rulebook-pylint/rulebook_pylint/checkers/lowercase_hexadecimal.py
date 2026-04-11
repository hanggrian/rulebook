from __future__ import annotations

from astroid.nodes import Const
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class LowercaseHexadecimalChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#lowercase-hexadecimal"""
    _MSG: str = 'lowercase.hexadecimal'

    name: str = 'lowercase-hexadecimal'
    msgs: dict[str, tuple[str, str, str]] = Messages.of(_MSG)

    def visit_const(self, node: Const) -> None:
        # checks for violation
        if not isinstance(node.value, int):
            return
        line: bytes = node.root().stream().readlines()[node.fromlineno - 1]
        value: str = line.decode('UTF-8')[node.col_offset: node.end_col_offset]
        if not value.lower().startswith('0x'):
            return
        value_replacement: str = value.lower()
        if value == value_replacement:
            return
        self.add_message(self._MSG, node=node, args=value_replacement)


def register(linter: PyLinter) -> None:
    linter.register_checker(LowercaseHexadecimalChecker(linter))
