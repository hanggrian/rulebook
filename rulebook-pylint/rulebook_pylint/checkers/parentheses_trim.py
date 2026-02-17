from __future__ import annotations

from tokenize import NL, OP, TokenInfo

from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ParenthesesTrimChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#parentheses-trim"""
    _MSG_FIRST: str = 'parentheses.trim.first'
    _MSG_LAST: str = 'parentheses.trim.last'

    _OPENING_PARENTHESES: set[str] = {'(', '[', '{'}
    _CLOSING_PARENTHESES: set[str] = {')', ']', '}'}

    name: str = 'parentheses-trim'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG_FIRST, _MSG_LAST)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        for i, token in enumerate(tokens):
            # find opening and closing parentheses
            if token.type is not OP:
                continue
            if token.string in self._OPENING_PARENTHESES:
                # checks for violation
                if i + 2 >= len(tokens):
                    continue
                next_token: TokenInfo = tokens[i + 1]
                next_token2: TokenInfo = tokens[i + 2]
                if next_token.type is not NL or next_token2.type is not NL:
                    continue
                self.add_message(
                    self._MSG_FIRST,
                    line=next_token2.start[0],
                    col_offset=next_token2.start[1],
                    args=token.string,
                )

            # checks for violation
            if token.string not in self._CLOSING_PARENTHESES:
                continue
            if i - 2 < 0:
                continue
            prev_token: TokenInfo = tokens[i - 1]
            prev_token2: TokenInfo = tokens[i - 2]
            if prev_token.type is not NL or prev_token2.type is not NL:
                continue
            self.add_message(
                self._MSG_LAST,
                line=prev_token.start[0],
                col_offset=prev_token.start[1],
                args=token.string,
            )


def register(linter: PyLinter) -> None:
    linter.register_checker(ParenthesesTrimChecker(linter))
