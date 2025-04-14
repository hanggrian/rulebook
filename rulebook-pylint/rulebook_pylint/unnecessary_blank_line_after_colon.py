from tokenize import TokenInfo, OP, NEWLINE, NL

from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookTokenChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class UnnecessaryBlankLineAfterColonChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#unnecessary-blank-line-after-colon"""
    MSG: str = 'unnecessary-blank-line-after-colon'

    name: str = 'unnecessary-blank-line-after-colon'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        for i, token in enumerate(tokens):
            # target colon operator
            if token.type != OP or token.string != ':':
                continue

            # checks for violation
            if i + 2 >= len(tokens):
                continue
            next_token: TokenInfo = tokens[i + 1]
            next_token2: TokenInfo = tokens[i + 2]
            if next_token.type != NEWLINE or next_token2.type != NL:
                continue
            self.add_message(self.MSG, line=next_token2.start[0], col_offset=next_token2.start[1])


def register(linter: 'PyLinter') -> None:
    linter.register_checker(UnnecessaryBlankLineAfterColonChecker(linter))
