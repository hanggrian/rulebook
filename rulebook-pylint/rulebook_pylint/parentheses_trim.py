from tokenize import TokenInfo, OP, NEWLINE, NL

from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookTokenChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ParenthesesTrimChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#parentheses-trim"""
    MSG_FIRST: str = 'parentheses-trim-first'
    MSG_LAST: str = 'parentheses-trim-last'

    name: str = 'parentheses-trim'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_FIRST, MSG_LAST)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        for i, token in enumerate(tokens):
            # find opening and closing parentheses
            if token.type != OP:
                continue
            if token.string in {'(', '[', '{'}:
                # checks for violation
                if i + 2 >= len(tokens):
                    continue
                next_token: TokenInfo = tokens[i + 1]
                next_token2: TokenInfo = tokens[i + 2]
                if next_token.type != NL or next_token2.type != NL:
                    continue
                self.add_message(
                    self.MSG_FIRST,
                    line=next_token2.start[0],
                    col_offset=next_token2.start[1],
                    args=token.string,
                )
            # checks for violation
            if token.string not in {')', ']', '}'}:
                continue
            if i - 2 < 0:
                continue
            prev_token: TokenInfo = tokens[i - 1]
            prev_token2: TokenInfo = tokens[i - 2]
            if prev_token.type != NL or prev_token2.type != NL:
                continue
            self.add_message(
                self.MSG_LAST,
                line=prev_token.start[0],
                col_offset=prev_token.start[1],
                args=token.string,
            )


def register(linter: 'PyLinter') -> None:
    linter.register_checker(ParenthesesTrimChecker(linter))
