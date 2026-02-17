from __future__ import annotations

from tokenize import NL, OP, TokenInfo

from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ParenthesesClipChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#parentheses-clip"""
    _MSG: str = 'parentheses.clip'

    _PARENTHESES: dict[str, str] = {
        '{': '}',
        '(': ')',
        '[': ']',
    }
    _OPENING_PARENTHESES: set[str] = {'{', '(', '['}

    name: str = 'parentheses-clip'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        for i, token in enumerate(tokens):
            # find opening parenthesis
            if token.type is not OP or token.string not in self._OPENING_PARENTHESES:
                continue
            self._process(tokens, i, token)

    def _process(self, tokens: list[TokenInfo], i: int, token: TokenInfo) -> None:
        j: int = i + 1
        end_parenthesis: str = self._PARENTHESES[token.string]

        # compare position when there is only whitespace between parentheses
        if j < len(tokens):
            next_token: TokenInfo = tokens[j]
            if next_token.type is OP and \
                next_token.string == end_parenthesis:
                # checks for violation
                if token.end[1] != next_token.start[1]:
                    self.add_message(
                        self._MSG,
                        line=token.start[0],
                        col_offset=token.start[1],
                        end_lineno=next_token.end[0],
                        end_col_offset=next_token.end[1],
                        args=token.string + end_parenthesis,
                    )
                return

        # otherwise iterate to determine newline
        has_newline: bool = False
        while j < len(tokens):
            curr_token: TokenInfo = tokens[j]
            # checks for violation
            if curr_token.type is NL:
                has_newline = True
            elif curr_token.type is OP and \
                curr_token.string == end_parenthesis:
                if has_newline:
                    self.add_message(
                        self._MSG,
                        line=token.start[0],
                        col_offset=token.start[1],
                        end_lineno=curr_token.end[0],
                        end_col_offset=curr_token.end[1],
                        args=token.string + end_parenthesis,
                    )
                    return
            else:
                return
            j += 1


def register(linter: PyLinter) -> None:
    linter.register_checker(ParenthesesClipChecker(linter))
