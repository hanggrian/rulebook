from tokenize import TokenInfo, COMMENT
from typing import TYPE_CHECKING

from pylint.typing import MessageDefinitionTuple
from regex import regex, Pattern
from rulebook_pylint.checkers import TokenChecker
from rulebook_pylint.internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class EmptyCommentLineJoiningChecker(TokenChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#empty-comment-line-joining
    """
    MSG: str = 'empty-comment-line-joining'
    EMPTY_COMMENT_REGEX: Pattern = regex.compile('#\s*$')

    name: str = 'empty-comment-line-joining'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        last_empty_token: TokenInfo | None = None
        for token in tokens:
            # first line of filter
            if token.type != COMMENT:
                continue

            # checks for violation
            if not EmptyCommentLineJoiningChecker.EMPTY_COMMENT_REGEX.match(token.string):
                continue
            if not last_empty_token:
                last_empty_token = token
                continue
            if last_empty_token.start[0] + 1 == token.start[0]:
                self.add_message(EmptyCommentLineJoiningChecker.MSG, line=token.start[0])

            # keep previous token for comparison
            last_empty_token = token

def register(linter: 'PyLinter') -> None:
    linter.register_checker(EmptyCommentLineJoiningChecker(linter))
