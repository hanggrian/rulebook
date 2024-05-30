from tokenize import TokenInfo, NL
from typing import TYPE_CHECKING

from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import TokenChecker
from rulebook_pylint.internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class EmptyLineJoiningChecker(TokenChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#empty-line-joining
    """
    MSG: str = 'empty-line-joining'

    name: str = 'empty-line-joining'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        prev_token_type: int | None = None
        for token in tokens:
            # checks for violation
            if token.type == NL and prev_token_type == NL:
                self.add_message(EmptyLineJoiningChecker.MSG, line=token.start[0])

            # keep previous token for comparison
            prev_token_type = token.type

def register(linter: 'PyLinter') -> None:
    linter.register_checker(EmptyLineJoiningChecker(linter))
