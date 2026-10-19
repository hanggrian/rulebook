from __future__ import annotations

from re import Pattern, compile as re

from astroid.nodes import ClassDef, Const
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class RuleHasDocumentationChecker(RulebookChecker):
    name: str = 'rule-has-documentation'
    msgs = {
        'W9902': ('Missing URL.', 'rule-has-documentation-missing', 'Add URL.'),
        'W9903': ('Invalid URL.', 'rule-has-documentation-invalid', 'Fix URL.'),
    }

    _REGEX: Pattern = re(r'([a-z])([A-Z])')

    def visit_classdef(self, node: ClassDef) -> None:
        name: str = node.name
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

        doc: Const | None = node.doc_node.value if node.doc_node else None
        if not doc:
            self.add_message('rule-has-documentation-missing', node=node)
            return

        kebab_name: str = self._REGEX.sub(r'\1-\2', base_name).lower()
        normalized_doc: str = ' '.join(line.strip() for line in doc.splitlines() if line.strip())
        if normalized_doc == \
            f'See detail: https://hanggrian.github.io/rulebook/rules/#{kebab_name}':
            return
        self.add_message('rule-has-documentation-invalid', node=node)


def register(linter: PyLinter) -> None:
    linter.register_checker(RuleHasDocumentationChecker(linter))
