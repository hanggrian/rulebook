from __future__ import annotations

from astroid.nodes import ClassDef, Const, FunctionDef, Module
from pylint.typing import Options, TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages
from rulebook_pylint.options import MAX_LINE_LENGTH_OPTION

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class BlockCommentClipChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#block-comment-clip"""
    _MSG: str = 'block.comment.clip'

    _SINGLELINE_TEMPLATE = 6  # """"""

    name: str = 'block-comment-clip'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)
    options: Options = (
        MAX_LINE_LENGTH_OPTION,
    )

    _max_line_length: int = 100

    def open(self) -> None:
        self._max_line_length = self.linter.config.rulebook_max_line_length

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
        if '\n' not in docstring_val:
            return
        line: str = docstring_val.strip()
        if '\n' in line:
            return
        text_length: int = docstring.col_offset + len(line)
        if text_length + self._SINGLELINE_TEMPLATE <= self._max_line_length:
            self.add_message(self._MSG, node=docstring)


def register(linter: PyLinter) -> None:
    linter.register_checker(BlockCommentClipChecker(linter))
