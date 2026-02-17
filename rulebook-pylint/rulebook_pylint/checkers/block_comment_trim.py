from __future__ import annotations

from re import Pattern, compile as re

from astroid.nodes import ClassDef, Const, FunctionDef, Module
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class BlockCommentTrimChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#block-comment-trim"""
    _MSG_FIRST: str = 'block.comment.trim.first'
    _MSG_LAST: str = 'block.comment.trim.last'

    _MULTIPLE_EMPTY_LINES: Pattern = re(r'\n\n\s*$')

    name: str = 'block-comment-trim'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG_FIRST, _MSG_LAST)

    def visit_module(self, node: Module) -> None:
        self._process(node.doc_node)

    def visit_classdef(self, node: ClassDef) -> None:
        self._process(node.doc_node)

    def visit_functiondef(self, node: FunctionDef) -> None:
        self._process(node.doc_node)

    def _process(self, docstring: Const | None) -> None:
        # checks for violation
        if not docstring:
            return
        docstring_val: str = docstring.value
        if docstring_val.startswith('\n\n'):
            self.add_message(self._MSG_FIRST, node=docstring, line=docstring.lineno)
        if self._MULTIPLE_EMPTY_LINES.search(docstring_val):
            self.add_message(self._MSG_LAST, node=docstring, line=docstring.end_lineno)


def register(linter: PyLinter) -> None:
    linter.register_checker(BlockCommentTrimChecker(linter))
