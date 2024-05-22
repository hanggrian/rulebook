from tokenize import tokenize, NL
from typing import TYPE_CHECKING

from astroid import Module
from pylint.checkers import BaseRawFileChecker
from pylint.typing import MessageDefinitionTuple

from .internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class EmptyLineWrappingChecker(BaseRawFileChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#empty-line-wrapping
    """
    MSG: str = 'empty-line-wrapping'

    name: str = 'empty-line-wrapping'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)

    def process_module(self, node: Module) -> None:
        with node.stream() as stream:
            prev_token_type: int | None = None
            for token in tokenize(stream.readline):
                # checks for violation
                if token.type == NL and prev_token_type == NL:
                    self.add_message(EmptyLineWrappingChecker.MSG, line=token.start[0])

                # keep previous token for comparison
                prev_token_type = token.type

def register(linter: 'PyLinter') -> None:
    linter.register_checker(EmptyLineWrappingChecker(linter))
