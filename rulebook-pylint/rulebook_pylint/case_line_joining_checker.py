from tokenize import TokenInfo, NAME, NL, INDENT, DEDENT
from typing import TYPE_CHECKING

from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import TokenChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class CaseLineJoiningChecker(TokenChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#case-line-joining
    """
    MSG: str = 'case-line-joining'

    name: str = 'case-line-joining'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        prev_token: TokenInfo | None = None
        token: TokenInfo
        for token in tokens:
            # checks for violation
            if token.type == NAME and token.string == 'case' and prev_token.type == NL:
                self.add_message(self.MSG, line=prev_token.start[0], col_offset=prev_token.start[1])

            # keep previous token for comparison
            if token.type not in (INDENT, DEDENT):
                prev_token = token


def register(linter: 'PyLinter') -> None:
    linter.register_checker(CaseLineJoiningChecker(linter))
