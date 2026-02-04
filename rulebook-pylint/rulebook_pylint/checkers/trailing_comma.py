from tokenize import TokenInfo, OP, NL, COMMENT, NAME

from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class TrailingCommaChecker(RulebookTokenChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#trailing-comma-in-call"""
    MSG_SINGLE: str = 'trailing.comma.single'
    MSG_MULTI: str = 'trailing.comma.multi'

    name: str = 'trailing-comma'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(MSG_SINGLE, MSG_MULTI)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        # filter out comments
        tokens = [t for t in tokens if t.type is not COMMENT]

        token: TokenInfo
        for i, token in enumerate(tokens):
            # find closing parenthesis
            if token.type is not OP or \
                token.string not in [')', ']', '}']:
                continue

            # skip sole generator like `any(...)`
            if self._is_sole_generator(tokens, i):
                continue

            # checks for violation
            prev_token: TokenInfo = tokens[i - 1]
            prev_token2: TokenInfo = tokens[i - 2]
            if prev_token.type is OP and prev_token.string == ',':
                self.add_message(
                    self.MSG_SINGLE,
                    line=prev_token.start[0],
                    col_offset=prev_token.end[1],
                )
                continue
            if prev_token.type is not NL:
                continue
            if prev_token2.type is OP and prev_token2.string == ',':
                continue

            self.add_message(
                self.MSG_MULTI,
                line=prev_token2.start[0],
                col_offset=prev_token2.end[1],
            )

    @staticmethod
    def _is_sole_generator(tokens: list[TokenInfo], close_index: int) -> bool:
        nesting: int = 0
        has_for: bool = False
        has_comma_at_root: bool = False
        for i in range(close_index - 1, -1, -1):
            token = tokens[i]
            if token.type is OP:
                if token.string in {')', ']', '}'}:
                    nesting += 1
                elif token.string in {'(', '[', '{'}:
                    if nesting == 0:
                        return has_for and not has_comma_at_root
                    nesting -= 1
                elif token.string == ',' and nesting == 0:
                    if i != close_index - 1:
                        has_comma_at_root = True
            elif token.type == NAME and token.string == 'for' and nesting == 0:
                has_for = True
                has_comma_at_root = False
        return False


def register(linter: 'PyLinter') -> None:
    linter.register_checker(TrailingCommaChecker(linter))
