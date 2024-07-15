from tokenize import TokenInfo, ENCODING, NL
from typing import TYPE_CHECKING

from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import TokenChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class FileInitialLineTrimmingChecker(TokenChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#file-initial-line-trimming
    """
    MSG: str = 'file-initial-line-trimming'

    name: str = 'file-initial-line-trimming'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        token: TokenInfo
        for token in tokens:
            # skip metadata
            type2: int = token.type
            if type2 == ENCODING:
                continue

            # checks for violation
            if type2 == NL:
                self.add_message(self.MSG, line=0)
            return


def register(linter: 'PyLinter') -> None:
    linter.register_checker(FileInitialLineTrimmingChecker(linter))
