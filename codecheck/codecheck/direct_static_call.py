from __future__ import annotations

from os.path import basename

from astroid.nodes import Attribute, Call, NodeNG, Name
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class DirectStaticCallChecker(RulebookChecker):
    name: str = 'direct-static-call'
    msgs = {
        'W9903': ('Call static member directly.', 'direct-static-call', 'Import static member.'),
    }

    _skip: bool

    def visit_module(self, node) -> None:
        self._skip = basename(node.file).startswith('test_')

    def visit_call(self, node: Call) -> None:
        if self._skip:
            return
        func: NodeNG | Attribute = node.func
        if not isinstance(func, Attribute):
            return
        self._process(func.expr)

    def visit_attribute(self, node: Attribute):
        if self._skip:
            return
        self._process(node.expr)

    def _process(self, node: NodeNG) -> None:
        if not isinstance(node, Name):
            return
        name: str = node.name
        if not name[0].isupper() or not any(c.islower() for c in name):
            return
        self.add_message('direct-static-call', node=node)


def register(linter: PyLinter) -> None:
    linter.register_checker(DirectStaticCallChecker(linter))
