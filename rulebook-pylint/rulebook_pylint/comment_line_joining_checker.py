from tokenize import TokenInfo, COMMENT
from typing import TYPE_CHECKING

from pylint.typing import MessageDefinitionTuple
from regex import regex, Pattern
from rulebook_pylint.checkers import TokenChecker
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import is_comment_empty

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class CommentLineJoiningChecker(TokenChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#comment-line-joining
    """
    MSG: str = 'comment-line-joining'

    EMPTY_COMMENT_REGEX: Pattern = regex.compile('#\s*$')

    name: str = 'comment-line-joining'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        last_empty_token: TokenInfo | None = None
        token: TokenInfo
        for token in tokens:
            # target comment
            if token.type != COMMENT:
                continue

            # checks for violation
            if not is_comment_empty(token):
                continue
            if not last_empty_token:
                last_empty_token = token
                continue
            if last_empty_token.start[0] + 1 == token.start[0]:
                self.add_message(self.MSG, line=token.start[0], col_offset=token.start[1])

            # keep previous token for comparison
            last_empty_token = token


def register(linter: 'PyLinter') -> None:
    linter.register_checker(CommentLineJoiningChecker(linter))
