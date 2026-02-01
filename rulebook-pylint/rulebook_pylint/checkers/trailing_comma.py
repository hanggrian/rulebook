from tokenize import TokenInfo, OP, NL, COMMENT

from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class TrailingCommaChecker(RulebookTokenChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#trailing-comma-in-call"""
    MSG_SINGLE: str = 'trailing.comma.single'
    MSG_MULTI: str = 'trailing.comma.multi'

    name: str = 'trailing-comma'
    msgs: dict[str, MessageDefinitionTuple] = _Messages.of(MSG_SINGLE, MSG_MULTI)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        # filter out comments
        tokens = [t for t in tokens if t.type != COMMENT]

        token: TokenInfo
        for i, token in enumerate(tokens):
            # find closing parenthesis
            if token.type != OP or \
                token.string not in [')', ']', '}']:
                continue

            # checks for violation
            prev_token: TokenInfo = tokens[i - 1]
            prev_token2: TokenInfo = tokens[i - 2]
            if prev_token.type == OP and prev_token.string == ',':
                self.add_message(
                    self.MSG_SINGLE,
                    line=prev_token.start[0],
                    col_offset=prev_token.end[1],
                )
                continue
            if prev_token.type != NL:
                continue
            if prev_token2.type == OP and prev_token2.string == ',':
                continue
            self.add_message(
                self.MSG_MULTI,
                line=prev_token2.start[0],
                col_offset=prev_token2.end[1],
            )


def register(linter: 'PyLinter') -> None:
    linter.register_checker(TrailingCommaChecker(linter))
