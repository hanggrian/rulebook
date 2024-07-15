from tokenize import TokenInfo, COMMENT
from typing import TYPE_CHECKING

from pylint.typing import MessageDefinitionTuple
from regex import regex, Pattern
from rulebook_pylint.checkers import TokenChecker
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import is_newline_single, is_comment_empty

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class CommentLineTrimmingChecker(TokenChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#comment-line-trimming
    """
    MSG: str = 'comment-line-trimming'

    EMPTY_COMMENT_REGEX: Pattern = regex.compile('#\s*$')

    name: str = 'comment-line-trimming'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        token: TokenInfo
        for i, token in enumerate(tokens):
            # first line of filter
            if token.type != COMMENT:
                continue

            # continue if this comment is first line
            if is_newline_single(tokens[i - 1]) and \
                tokens[i - 2].type == COMMENT:
                return

            # iterate to find last
            j = i
            while j + 2 < len(tokens) and \
                is_newline_single(tokens[j + 1]) and \
                tokens[j + 2].type == COMMENT:
                j += 2
            current_token: TokenInfo = tokens[j]

            # skip blank comment
            if current_token is token:
                return

            # checks for violation
            if is_comment_empty(token):
                self.add_message(self.MSG, line=token.start[0], col_offset=token.start[1])
            if is_comment_empty(current_token):
                self.add_message(
                    self.MSG,
                    line=current_token.start[0],
                    col_offset=current_token.start[1],
                )
            return


def register(linter: 'PyLinter') -> None:
    linter.register_checker(CommentLineTrimmingChecker(linter))
