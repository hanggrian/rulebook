from tokenize import TokenInfo, STRING
from typing import TYPE_CHECKING

from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import TokenChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class StringLiteralQuotationChecker(TokenChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#string-literal-quotation
    """
    MSG_SINGLE: str = 'string-literal-quotation-single'
    MSG_DOUBLE: str = 'string-literal-quotation-double'

    name: str = 'string-literal-quotation'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_SINGLE)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        token: TokenInfo
        for token in tokens:
            # target string
            if token.type != STRING:
                continue

            # ignore docstring
            content: str = token.string
            if self._is_surrounded_by(content, '"""') or self._is_surrounded_by(content, "'''"):
                continue

            # checks for violation
            if self._is_surrounded_by(content, '"'):
                if "'" in content[1:-1]:
                    continue
                self.add_message(
                    self.MSG_SINGLE,
                    line=token.start[0],
                    col_offset=token.start[1],
                    end_lineno=token.end[0],
                    end_col_offset=token.end[1],
                )
                continue
            if "'" not in content[1:-1]:
                continue
            self.add_message(
                self.MSG_DOUBLE,
                line=token.start[0],
                col_offset=token.start[1],
                end_lineno=token.end[0],
                end_col_offset=token.end[1],
            )

    @staticmethod
    def _is_surrounded_by(text: str, prefix_suffix: str) -> bool:
        return text.startswith(prefix_suffix) and \
            text.endswith(prefix_suffix)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(StringLiteralQuotationChecker(linter))
