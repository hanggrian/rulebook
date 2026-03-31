from __future__ import annotations

from astroid.nodes import Assign, AnnAssign, AssignName, Call, ClassDef, Name, NodeNG, Set
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class StaticImmutableCollection(RulebookChecker):
    name: str = 'static-immutable-collection'
    msgs = {
        'W9905': (
            'Make collection immutable.',
            'static-immutable-collection',
            'Replace collection.',
        ),
    }

    def visit_classdef(self, node: ClassDef) -> None:
        for assign in node.body:
            if isinstance(assign, Assign):
                if len(assign.targets) != 1:
                    continue
                target: NodeNG = assign.targets[0]
                if not isinstance(target, AssignName) or \
                    any(c.islower() for c in target.name):
                    continue
                self._check_value(assign.value)
            elif isinstance(assign, AnnAssign):
                if not isinstance(assign.target, AssignName) or \
                    any(c.islower() for c in assign.target.name):
                    continue
                if assign.value is None:
                    continue
                self._check_value(assign.value)

    def _check_value(self, value: NodeNG) -> None:
        if isinstance(value, Call) and \
            isinstance(value.func, Name) and \
            value.func.name == 'set':
            self.add_message('static-immutable-collection', node=value)
        elif isinstance(value, Set):
            self.add_message('static-immutable-collection', node=value)


def register(linter: PyLinter):
    linter.register_checker(StaticImmutableCollection(linter))
