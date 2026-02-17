from __future__ import annotations

from codecs import decode

from astroid.nodes import Module
from pylint.typing import TYPE_CHECKING
from regex import IGNORECASE, Match, Pattern, compile as regex

from rulebook_pylint.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


# pylint: disable=todo-comment
class TodoCommentChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#todo-comment"""
    _MSG_KEYWORD: str = 'todo.comment.keyword'
    _MSG_SEPARATOR: str = 'todo.comment.separator'

    _KEYWORD_REGEX: Pattern = regex(r'\b(?i:fixme|todo)(?<!FIXME|TODO)\b')
    _SEPARATOR_REGEX: Pattern = regex(r'\b(todo|fixme)\S', IGNORECASE)

    name: str = 'todo-comment'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG_KEYWORD, _MSG_SEPARATOR)

    def process_module(self, node: Module) -> None:
        with node.stream() as stream:
            for i, line in enumerate(stream):
                # obtain comment content
                line_no: int = i + 1
                parts: list[bytes] = line.split(b'#', 1)
                if len(parts) < 2:
                    continue
                comment_content: str = decode(parts[1])

                # checks for violation
                match: Match = self._KEYWORD_REGEX.search(comment_content)
                if match:
                    self.add_message(self._MSG_KEYWORD, line=line_no, args=match.group(0))
                match: Match = self._SEPARATOR_REGEX.search(comment_content)
                if match:
                    self.add_message(self._MSG_SEPARATOR, line=line_no, args=match.group(0)[-1])


def register(linter: PyLinter) -> None:
    linter.register_checker(TodoCommentChecker(linter))
