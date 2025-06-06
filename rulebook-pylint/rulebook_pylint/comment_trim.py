from tokenize import TokenInfo, COMMENT, NL

from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookTokenChecker
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import is_comment_empty

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class CommentTrimChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#comment-trim"""
    MSG: str = 'comment-trim'

    name: str = 'comment-trim'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        for i, token in enumerate(tokens):
            # target comment
            if token.type != COMMENT:
                continue

            # continue if this comment is first line
            if i - 2 >= 0 and \
                tokens[i - 1].type == NL and \
                tokens[i - 2].type == COMMENT:
                return

            # iterate to find last
            j: int = i
            while j + 2 < len(tokens) and \
                tokens[j + 1].type == NL and \
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
    linter.register_checker(CommentTrimChecker(linter))
