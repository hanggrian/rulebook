from tokenize import TokenInfo, INDENT, DEDENT, ENDMARKER, NEWLINE, NL, COMMENT, FSTRING_START, \
    FSTRING_END

from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class DuplicateSpaceChecker(RulebookTokenChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#duplicate-space"""
    MSG: str = 'duplicate-space'

    name: str = 'duplicate-space'
    msgs: dict[str, MessageDefinitionTuple] = _Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        fstring_flag: bool = False
        for i, token in enumerate(tokens):
            # get last token to compare
            last_token: TokenInfo = tokens[i - 1]

            if last_token.type == FSTRING_END:
                fstring_flag = False

            # checks for violation
            if not fstring_flag and self._is_duplicate_space(token, last_token):
                self.add_message(self.MSG, line=last_token.start[0], col_offset=last_token.start[1])

            if token.type == FSTRING_START:
                fstring_flag = True

    @staticmethod
    def _is_duplicate_space(token: TokenInfo, last_token: TokenInfo) -> bool:
        if any(
            (t in (NEWLINE, NL, INDENT, DEDENT, ENDMARKER) \
             for t in (token.type, last_token.type)),
        ):
            return False
        return token.start[1] - last_token.end[1] > 2 \
            if token.type == COMMENT \
            else token.start[1] - last_token.end[1] > 1


def register(linter: 'PyLinter') -> None:
    linter.register_checker(DuplicateSpaceChecker(linter))
