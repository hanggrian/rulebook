from __future__ import annotations

from tokenize import COMMENT, DEDENT, ENDMARKER, INDENT, NEWLINE, NL, TokenInfo

from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class DuplicateSpaceChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#duplicate-space"""
    _MSG: str = 'duplicate.space'

    name: str = 'duplicate-space'
    msgs: dict[str, tuple[str, str, str]] = Messages.of(_MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        # fstring_flag: bool = False
        for i, token in enumerate(tokens):
            # get last token to compare
            last_token: TokenInfo = tokens[i - 1]

            # FSTRING_END unavailable on 3.11
            # if last_token.type == FSTRING_END:
            #     fstring_flag = False

            # checks for violation
            # if fstring_flag or not self._is_duplicate_space(token, last_token):
            if not self._is_duplicate_space(token, last_token):
                continue
            self.add_message(
                self._MSG,
                line=last_token.start[0],
                col_offset=last_token.start[1],
            )

            # if token.type == FSTRING_START:
            #     fstring_flag = True

    @staticmethod
    def _is_duplicate_space(token: TokenInfo, last_token: TokenInfo) -> bool:
        if any(
            t in (NEWLINE, NL, INDENT, DEDENT, ENDMARKER)
            for t in (token.type, last_token.type)
        ):
            return False
        return token.start[1] - last_token.end[1] > 2 \
            if token.type == COMMENT \
            else token.start[1] - last_token.end[1] > 1


def register(linter: PyLinter) -> None:
    linter.register_checker(DuplicateSpaceChecker(linter))
