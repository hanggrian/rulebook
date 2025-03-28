import regex
from astroid import Const, Module, ClassDef, FunctionDef
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class BlockCommentTrimChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/all/#block-comment-trim"""
    MSG_FIRST: str = 'block-comment-trim-first'
    MSG_LAST: str = 'block-comment-trim-last'

    name: str = 'block-comment-trim'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_FIRST, MSG_LAST)

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
        if docstring.value.startswith('\n\n'):
            self.add_message(self.MSG_FIRST, node=docstring)
        if regex.search(r'\n\n\s*$', docstring.value):
            self.add_message(self.MSG_LAST, node=docstring)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(BlockCommentTrimChecker(linter))
