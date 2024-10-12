from tokenize import TokenInfo, COMMENT

import regex
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from regex import Pattern
from rulebook_pylint.checkers import RulebookTokenChecker
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import is_comment_empty

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class CommentLineJoiningChecker(RulebookTokenChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#comment-line-joining
    """
    MSG: str = 'comment-line-joining'

    EMPTY_COMMENT_REGEX: Pattern = regex.compile('#\s*$')

    name: str = 'comment-line-joining'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        last_empty_token: TokenInfo | None = None
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
