from __future__ import annotations

from astroid.nodes import ClassDef, Name
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ExceptionInheritanceChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#exception-inheritance"""
    _MSG: str = 'exception.inheritance'

    name: str = 'exception-inheritance'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        for base in [
            n for n in node.bases
            if isinstance(n, Name) and n.name == 'BaseException'
        ]:
            self.add_message(self._MSG, node=base)
            continue


def register(linter: PyLinter) -> None:
    linter.register_checker(ExceptionInheritanceChecker(linter))
