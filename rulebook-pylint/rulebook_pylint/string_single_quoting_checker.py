from tokenize import TokenInfo, STRING
from typing import TYPE_CHECKING

from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import TokenChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class StringSingleQuotingChecker(TokenChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#string-single-quoting
    """
    MSG: str = 'string-single-quoting'

    name: str = 'string-single-quoting'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        token: TokenInfo
        for token in tokens:
            # target string
            if token.type != STRING:
                continue

            # checks for violation
            content: str = token.string
            if (content.startswith('"""') or content.endswith('"""')) or \
                (not content.startswith('"') and not content.endswith('"')) or \
                "'" in content[1:-1]:
                continue
            self.add_message(
                self.MSG,
                line=token.start[0],
                col_offset=token.start[1],
                end_lineno=token.end[0],
                end_col_offset=token.end[1],
            )


def register(linter: 'PyLinter') -> None:
    linter.register_checker(StringSingleQuotingChecker(linter))
