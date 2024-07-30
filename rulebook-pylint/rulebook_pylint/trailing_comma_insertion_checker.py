from tokenize import TokenInfo, OP, NL, COMMENT
from typing import TYPE_CHECKING

from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import TokenChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class TrailingCommaInsertionChecker(TokenChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#trailing-comma-insertion
    """
    MSG: str = 'trailing-comma-insertion'

    name: str = 'trailing-comma-insertion'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        # filter out comments
        tokens = [t for t in tokens if t.type != COMMENT]

        token: TokenInfo
        for i, token in enumerate(tokens):
            # find closing parenthesis
            if token.type != OP or token.string != ')':
                continue

            # checks for violation
            prev_token: TokenInfo = tokens[i - 1]
            prev_token2: TokenInfo = tokens[i - 2]
            if prev_token.type != NL:
                continue
            if prev_token2.type == OP or prev_token2.string == ',':
                continue
            self.add_message(self.MSG, line=prev_token2.start[0], col_offset=prev_token2.end[1])


def register(linter: 'PyLinter') -> None:
    linter.register_checker(TrailingCommaInsertionChecker(linter))
