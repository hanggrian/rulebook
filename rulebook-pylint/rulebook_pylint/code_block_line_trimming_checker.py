from tokenize import TokenInfo, OP, NEWLINE, NL
from typing import TYPE_CHECKING

from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import TokenChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class CodeBlockLineTrimmingChecker(TokenChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#code-block-line-trimming
    """
    MSG: str = 'code-block-line-trimming'

    name: str = 'code-block-line-trimming'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        token: TokenInfo
        for i, token in enumerate(tokens):
            # target colon operator
            if token.type != OP or token.string != ':':
                continue

            # checks for violation
            next_token: TokenInfo = tokens[i + 1]
            next_token2: TokenInfo = tokens[i + 2]
            if i + 2 >= len(tokens) or next_token.type != NEWLINE or next_token2.type != NL:
                continue
            self.add_message(self.MSG, line=next_token2.start[0], col_offset=next_token2.start[1])


def register(linter: 'PyLinter') -> None:
    linter.register_checker(CodeBlockLineTrimmingChecker(linter))
