from __future__ import annotations

from tokenize import COMMENT, DEDENT, ENDMARKER, FSTRING_END, FSTRING_START, INDENT, NEWLINE, NL, \
    TokenInfo

from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class DuplicateSpaceChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#duplicate-space"""
    _MSG: str = 'duplicate.space'

    name: str = 'duplicate-space'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        fstring_flag: bool = False
        for i, token in enumerate(tokens):
            # get last token to compare
            last_token: TokenInfo = tokens[i - 1]

            if last_token.type is FSTRING_END:
                fstring_flag = False

            # checks for violation
            if not fstring_flag and self._is_duplicate_space(token, last_token):
                self.add_message(self._MSG, line=last_token.start[0], col_offset=last_token.start[1])

            if token.type is FSTRING_START:
                fstring_flag = True

    @staticmethod
    def _is_duplicate_space(token: TokenInfo, last_token: TokenInfo) -> bool:
        token_type: int = token.type
        if any(
            t in (NEWLINE, NL, INDENT, DEDENT, ENDMARKER)
            for t in (token_type, last_token.type)
        ):
            return False
        return token.start[1] - last_token.end[1] > 2 \
            if token_type is COMMENT \
            else token.start[1] - last_token.end[1] > 1


def register(linter: PyLinter) -> None:
    linter.register_checker(DuplicateSpaceChecker(linter))
