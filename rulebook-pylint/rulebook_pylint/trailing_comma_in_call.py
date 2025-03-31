from tokenize import TokenInfo, OP, NL, COMMENT

from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookTokenChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class TrailingCommaInCallChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/all/#trailing-comma-in-call"""
    MSG_SINGLE: str = 'trailing-comma-in-call-single'
    MSG_MULTI: str = 'trailing-comma-in-call-multi'

    name: str = 'trailing-comma-in-call'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_SINGLE, MSG_MULTI)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        # filter out comments
        tokens = [t for t in tokens if t.type != COMMENT]

        for i, token in enumerate(tokens):
            # find closing parenthesis
            if token.type != OP or token.string != ')':
                continue

            # checks for violation
            if i - 2 < 0:
                continue
            prev_token: TokenInfo = tokens[i - 1]
            prev_token2: TokenInfo = tokens[i - 2]
            if prev_token.type == OP and prev_token.string == ',':
                self.add_message(
                    self.MSG_SINGLE,
                    line=prev_token.start[0],
                    end_lineno=prev_token.end[0],
                    col_offset=prev_token.start[1],
                    end_col_offset=prev_token.end[1],
                )
                continue
            if prev_token.type != NL or \
                prev_token2.type == OP or \
                prev_token2.string == ',':
                continue
            self.add_message(
                self.MSG_MULTI,
                line=prev_token2.start[0],
                end_lineno=prev_token2.end[0],
                col_offset=prev_token2.start[1],
                end_col_offset=prev_token2.end[1],
            )


def register(linter: 'PyLinter') -> None:
    linter.register_checker(TrailingCommaInCallChecker(linter))
