from tokenize import TokenInfo, NAME, NL, INDENT, DEDENT
from typing import TYPE_CHECKING

from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import TokenChecker
from rulebook_pylint.internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class SwitchCaseJoiningChecker(TokenChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#switch-case-joining
    """
    MSG: str = 'switch-case-joining'

    name: str = 'switch-case-joining'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        prev_token_type: int | None = None
        for token in tokens:
            # checks for violation
            if token.type == NAME and token.string == 'case' and prev_token_type == NL:
                self.add_message(SwitchCaseJoiningChecker.MSG, line=token.start[0])

            # keep previous token for comparison
            if token.type != INDENT and token.type != DEDENT:
                prev_token_type = token.type

def register(linter: 'PyLinter') -> None:
    linter.register_checker(SwitchCaseJoiningChecker(linter))
