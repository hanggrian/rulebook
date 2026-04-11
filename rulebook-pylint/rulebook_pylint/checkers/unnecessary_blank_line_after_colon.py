from __future__ import annotations

from tokenize import NEWLINE, NL, OP, TokenInfo

from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class UnnecessaryBlankLineAfterColonChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#unnecessary-blank-line-after-colon"""
    _MSG: str = 'unnecessary.blank.line.after.colon'

    name: str = 'unnecessary-blank-line-after-colon'
    msgs: dict[str, tuple[str, str, str]] = Messages.of(_MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        for i, token in enumerate(tokens):
            # target colon operator
            if token.type != OP or \
                token.string != ':':
                continue

            # checks for violation
            if i + 2 >= len(tokens):
                continue
            next_token: TokenInfo = tokens[i + 1]
            next_token2: TokenInfo = tokens[i + 2]
            if next_token.type != NEWLINE or \
                next_token2.type != NL:
                continue
            self.add_message(self._MSG, line=next_token2.start[0], col_offset=next_token2.start[1])


def register(linter: PyLinter) -> None:
    linter.register_checker(UnnecessaryBlankLineAfterColonChecker(linter))
