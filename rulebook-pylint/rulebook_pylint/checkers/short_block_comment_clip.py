from astroid import Const, Module, ClassDef, FunctionDef
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple, Options
from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ShortBlockCommentClipChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#short-block-comment-clip"""
    MSG: str = 'short-block-comment-clip'

    SINGLELINE_TEMPLATE = 6  # """"""

    name: str = 'short-block-comment-clip'
    msgs: dict[str, MessageDefinitionTuple] = _Messages.of(MSG)
    options: Options = (
        (
            'rulebook-max-line-length',
            {
                'default': 100,
                'type': 'int',
                'metavar': '<int>',
                'help': 'Max length of a line.',
            },
        ),
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
        if '\n' not in docstring.value:
            return
        line: str = docstring.value.strip()
        if '\n' in line:
            return
        text_length: int = docstring.col_offset + len(line)
        if text_length + self.SINGLELINE_TEMPLATE <= self._max_line_length:
            self.add_message(self.MSG, node=docstring)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(ShortBlockCommentClipChecker(linter))
