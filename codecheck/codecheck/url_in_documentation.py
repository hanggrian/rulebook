from __future__ import annotations

from re import Pattern, compile as re

from astroid.nodes import ClassDef
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class UrlInDocumentation(RulebookChecker):
    name: str = 'url-in-documentation'
    msgs = {
        'W9901': ('Missing URL in docstring.', 'missing-url', 'Add URL.'),
        'W9902': ('Invalid URL in docstring.', 'invalid-url', 'Fix URL.'),
    }

    _REGEX: Pattern = re(r'([a-z])([A-Z])')

    def visit_classdef(self, node: ClassDef) -> None:
        name = node.name
        if name.startswith('Rulebook') or \
            name.startswith('Base') or \
            name.startswith('Test'):
            return
        if name.endswith('Rule'):
            base_name = name[:-4]
        elif name.endswith('Checker'):
            base_name = name[:-7]
        else:
            return

        doc = node.doc_node.value if node.doc_node else None
        if not doc:
            self.add_message('missing-url', node=node)
            return

        kebab_name = self._REGEX.sub(r'\1-\2', base_name).lower()
        if doc == f'See detail: https://hanggrian.github.io/rulebook/rules/#{kebab_name}':
            return
        self.add_message('invalid-url', node=node)


def register(linter: PyLinter) -> None:
    linter.register_checker(UrlInDocumentation(linter))
