from __future__ import annotations

from astroid.nodes import ClassDef, Const, FunctionDef, Module
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class DuplicateBlankLineInBlockCommentChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line-in-block-comment"""
    _MSG: str = 'duplicate.blank.line.in.block.comment'

    name: str = 'duplicate-blank-line-in-block-comment'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)

    def visit_module(self, node: Module) -> None:
        self._process(node.doc_node)

    def visit_classdef(self, node: ClassDef) -> None:
        self._process(node.doc_node)

    def visit_functiondef(self, node: FunctionDef) -> None:
        self._process(node.doc_node)

    def _process(self, docstring: Const | None) -> None:
        # checks for violation
        if not docstring or '\n\n\n' not in docstring.value:
            return
        self.add_message(self._MSG, node=docstring)


def register(linter: PyLinter) -> None:
    linter.register_checker(DuplicateBlankLineInBlockCommentChecker(linter))
