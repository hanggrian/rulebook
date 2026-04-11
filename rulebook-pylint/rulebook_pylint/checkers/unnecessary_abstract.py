from __future__ import annotations

from astroid.bases import Proxy
from astroid.nodes import ClassDef, FunctionDef, Name, NodeNG
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import Messages
from rulebook_pylint.nodes import has_decorator

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class UnnecessaryAbstractChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#unnecessary-abstract"""
    _MSG: str = 'unnecessary.abstract'

    name: str = 'unnecessary-abstract'
    msgs: dict[str, tuple[str, str, str]] = Messages.of(_MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # skip non-abstract class
        if not any(isinstance(n, Name) and n.name == 'ABC' for n in node.bases):
            return

        # checks for violation
        if len(node.bases) > 1 or \
            any(
                isinstance(n, FunctionDef) and
                has_decorator(n, 'abstractmethod')
                for n in node.body
            ):
            return
        base: NodeNG | Proxy = node.bases[0]
        self.add_message(self._MSG, node=base)


def register(linter: PyLinter) -> None:
    linter.register_checker(UnnecessaryAbstractChecker(linter))
