from tokenize import tokenize, NAME, NL, INDENT, DEDENT
from typing import TYPE_CHECKING

from astroid import Module
from pylint.checkers import BaseRawFileChecker
from pylint.typing import MessageDefinitionTuple

from .internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class SwitchStatementWrappingChecker(BaseRawFileChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#switch-statement-wrapping
    """
    MSG: str = 'switch-statement-wrapping'

    name: str = 'switch-statement-wrapping'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)

    def process_module(self, node: Module) -> None:
        with node.stream() as stream:
            prev_token_type: int | None = None
            for token in tokenize(stream.readline):
                # checks for violation
                if token.type == NAME and token.string == 'case' and prev_token_type == NL:
                    self.add_message(SwitchStatementWrappingChecker.MSG, line=token.start[0])

                # keep previous token for comparison
                if token.type != INDENT and token.type != DEDENT:
                    prev_token_type = token.type

def register(linter: 'PyLinter') -> None:
    linter.register_checker(SwitchStatementWrappingChecker(linter))
