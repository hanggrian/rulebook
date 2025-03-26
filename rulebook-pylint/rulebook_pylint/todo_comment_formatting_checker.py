from codecs import decode

import regex
from astroid import Module
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from regex import Pattern, Match, IGNORECASE
from rulebook_pylint.checkers import RulebookRawChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


# pylint: disable=todo-comment-formatting-keyword,todo-comment-formatting-separator
class TodoCommentFormattingChecker(RulebookRawChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#todo-comment-formatting"""
    MSG_KEYWORD: str = 'todo-comment-formatting-keyword'
    MSG_SEPARATOR: str = 'todo-comment-formatting-separator'

    KEYWORD_REGEX: Pattern = regex.compile(r'\b(?i:fixme|todo)(?<!FIXME|TODO)\b')
    SEPARATOR_REGEX: Pattern = regex.compile(r'\b(todo|fixme)\S', IGNORECASE)

    name: str = 'todo-comment-formatting'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_KEYWORD, MSG_SEPARATOR)

    def process_module(self, node: Module) -> None:
        with node.stream() as stream:
            line: bytes
            for i, line in enumerate(stream):
                # obtain comment content
                line_no: int = i + 1
                parts: list[bytes] = line.split(b'#', 1)
                if len(parts) < 2:
                    continue
                comment_content: str = decode(parts[1])

                # checks for violation
                match: Match = self.KEYWORD_REGEX.search(comment_content)
                if match:
                    self.add_message(self.MSG_KEYWORD, line=line_no, args=match.group(0))
                match: Match = self.SEPARATOR_REGEX.search(comment_content)
                if match:
                    self.add_message(self.MSG_SEPARATOR, line=line_no, args=match.group(0)[-1])


def register(linter: 'PyLinter') -> None:
    linter.register_checker(TodoCommentFormattingChecker(linter))
