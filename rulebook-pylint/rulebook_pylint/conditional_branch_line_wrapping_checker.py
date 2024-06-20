from tokenize import TokenInfo, NAME, NL, INDENT, DEDENT
from typing import TYPE_CHECKING

from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import TokenChecker
from rulebook_pylint.internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ConditionalBranchLineWrappingChecker(TokenChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#conditional-branch-line-wrapping
    """
    MSG: str = 'conditional-branch-line-wrapping'

    name: str = 'conditional-branch-line-wrapping'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        prev_token: TokenInfo | None = None
        for token in tokens:
            # checks for violation
            if token.type == NAME and token.string == 'case' and prev_token.type == NL:
                self.add_message(self.MSG, line=prev_token.start[0])

            # keep previous token for comparison
            if token.type != INDENT and token.type != DEDENT:
                prev_token = token


def register(linter: 'PyLinter') -> None:
    linter.register_checker(ConditionalBranchLineWrappingChecker(linter))
