from tokenize import tokenize, STRING
from typing import TYPE_CHECKING

from astroid import Module
from pylint.checkers import BaseRawFileChecker
from pylint.typing import MessageDefinitionTuple

from .internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class StringSingleQuotingChecker(BaseRawFileChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#string-single-quoting
    """
    MSG: str = 'string-single-quoting'

    name: str = 'string-single-quoting'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)

    def process_module(self, node: Module) -> None:
        with node.stream() as stream:
            for token in tokenize(stream.readline):
                # skip non-string
                if token.type != STRING:
                    continue

                # checks for violation
                content: str = token.string
                if (content.startswith('"""') or content.endswith('"""')) or \
                    (not content.startswith('"') and not content.endswith('"')) or \
                    "'" in content[1:-1]:
                    continue
                self.add_message(
                    StringSingleQuotingChecker.MSG,
                    line=token.start[0],
                    col_offset=token.start[1],
                    end_lineno=token.end[0],
                    end_col_offset=token.end[1],
                )

def register(linter: 'PyLinter') -> None:
    linter.register_checker(StringSingleQuotingChecker(linter))
