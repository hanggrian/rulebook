from codecs import decode
from typing import TYPE_CHECKING

import regex
from astroid import Module
from pylint.checkers import BaseRawFileChecker
from pylint.typing import MessageDefinitionTuple
from regex import Pattern, Match, IGNORECASE

from .internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class TodoCommentFormattingChecker(BaseRawFileChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#todo-comment-formatting
    """
    MSG_KEYWORD: str = 'todo-comment-formatting-keyword'
    MSG_SEPARATOR: str = 'todo-comment-formatting-separator'

    KEYWORD_REGEX: Pattern = regex.compile(r'\b(?i:fixme|todo)(?<!FIXME|TODO)\b')
    SEPARATOR_REGEX: Pattern = regex.compile(r'\b(todo|fixme)\S', IGNORECASE)

    name: str = 'todo-comment-formatting'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG_KEYWORD, MSG_SEPARATOR)

    def process_module(self, node: Module) -> None:
        with node.stream() as stream:
            for i, line in enumerate(stream):
                # obtain comment content
                line_no = i + 1
                parts: list[bytes] = line.split(b'#', 1)
                if len(parts) < 2:
                    continue
                comment_content: str = decode(parts[1])

                # checks for violation
                match: Match = TodoCommentFormattingChecker.KEYWORD_REGEX.search(comment_content)
                if match:
                    self.add_message(
                        TodoCommentFormattingChecker.MSG_KEYWORD,
                        line=line_no,
                        args=match.group(0),
                    )
                match: Match = TodoCommentFormattingChecker.SEPARATOR_REGEX.search(comment_content)
                if match:
                    self.add_message(
                        TodoCommentFormattingChecker.MSG_SEPARATOR,
                        line=line_no,
                        args=match.group(0)[-1],
                    )


def register(linter: 'PyLinter') -> None:
    linter.register_checker(TodoCommentFormattingChecker(linter))
