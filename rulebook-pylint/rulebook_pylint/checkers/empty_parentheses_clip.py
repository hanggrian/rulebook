from tokenize import TokenInfo, OP, NL

from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class EmptyParenthesesClipChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#empty-parentheses-clip"""
    MSG: str = 'empty-parentheses-clip'

    PARENTHESES: dict[str, str] = {
        '{': '}',
        '(': ')',
        '[': ']',
    }

    name: str = 'empty-parentheses-clip'
    msgs: dict[str, MessageDefinitionTuple] = _Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        for i, token in enumerate(tokens):
            # find opening parenthesis
            if token.type == OP and token.string in {'{', '(', '['}:
                self._process(tokens, i, token)

    def _process(self, tokens: list[TokenInfo], i: int, token: TokenInfo) -> None:
        j: int = i + 1
        end_parenthesis: str = self.PARENTHESES[token.string]

        # compare position when there is only whitespace between parentheses
        if j < len(tokens):
            next_token: TokenInfo = tokens[j]
            if next_token.type == OP and \
                next_token.string == end_parenthesis:
                # checks for violation
                if token.end[1] != next_token.start[1]:
                    self.add_message(
                        self.MSG,
                        line=token.start[0],
                        col_offset=token.start[1],
                        end_lineno=next_token.end[0],
                        end_col_offset=next_token.end[1],
                        args=token.string + end_parenthesis,
                    )
                return

        # otherwise iterate to determine newline
        has_newline: bool = False
        while j < len(tokens):
            current_token: TokenInfo = tokens[j]
            # checks for violation
            if current_token.type == NL:
                has_newline = True
            elif current_token.type == OP and \
                current_token.string == end_parenthesis:
                if has_newline:
                    self.add_message(
                        self.MSG,
                        line=token.start[0],
                        col_offset=token.start[1],
                        end_lineno=current_token.end[0],
                        end_col_offset=current_token.end[1],
                        args=token.string + end_parenthesis,
                    )
                    return
            else:
                return
            j += 1


def register(linter: 'PyLinter') -> None:
    linter.register_checker(EmptyParenthesesClipChecker(linter))
