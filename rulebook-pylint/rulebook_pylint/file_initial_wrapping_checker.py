from tokenize import tokenize, ENCODING, NL
from typing import TYPE_CHECKING

from astroid import Module
from pylint.checkers import BaseRawFileChecker
from pylint.typing import MessageDefinitionTuple

from .internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class FileInitialWrappingChecker(BaseRawFileChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#file-initial-wrapping
    """
    MSG: str = 'file-initial-wrapping'

    name: str = 'file-initial-wrapping'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)

    def process_module(self, node: Module) -> None:
        with node.stream() as stream:
            for token in tokenize(stream.readline):
                # skip metadata
                type2: int = token.type
                if type2 == ENCODING:
                    continue

                # checks for violation
                if type2 == NL:
                    self.add_message(FileInitialWrappingChecker.MSG, line=0)
                return None

def register(linter: 'PyLinter') -> None:
    linter.register_checker(FileInitialWrappingChecker(linter))
