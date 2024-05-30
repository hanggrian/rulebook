from tokenize import TokenInfo, ENCODING, NL
from typing import TYPE_CHECKING

from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import TokenChecker
from rulebook_pylint.internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class FileInitialJoiningChecker(TokenChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#file-initial-joining
    """
    MSG: str = 'file-initial-joining'

    name: str = 'file-initial-joining'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        for token in tokens:
            # skip metadata
            type2: int = token.type
            if type2 == ENCODING:
                continue

            # checks for violation
            if type2 == NL:
                self.add_message(FileInitialJoiningChecker.MSG, line=0)
            return None

def register(linter: 'PyLinter') -> None:
    linter.register_checker(FileInitialJoiningChecker(linter))
