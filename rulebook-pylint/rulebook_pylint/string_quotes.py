from tokenize import TokenInfo, STRING, FSTRING_START, FSTRING_MIDDLE, FSTRING_END

from astroid.nodes import Position
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookTokenChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class StringQuotesChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#string-quotes"""
    MSG_SINGLE: str = 'string-quotes-single'
    MSG_DOUBLE: str = 'string-quotes-double'

    name: str = 'string-quotes'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_SINGLE, MSG_DOUBLE)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        # collect from regular and f-string
        literals: dict[Position, str] = {}
        fstr_token: TokenInfo | None = None
        fstr: str | None = None
        for token in tokens:
            if token.type == STRING:
                literals[
                    Position(
                        token.start[0],
                        token.start[1],
                        token.end[0],
                        token.end[1],
                    )
                ] = token.string
            elif token.type == FSTRING_START:
                fstr_token = token
                fstr = token.string
            elif token.type == FSTRING_MIDDLE:
                fstr += token.string
            elif token.type == FSTRING_END:
                literals[
                    Position(
                        fstr_token.start[0],
                        fstr_token.start[1],
                        token.end[0],
                        token.end[1],
                    )
                ] = fstr + token.string

        # ignore docstring
        for position, literal in [
            (p, l) for (p, l) in literals.items() \
            if not l.startswith('"""') and \
               not l.startswith("'''")
        ]:
            # determine quote characters
            if literal.startswith('r') or \
                literal.startswith('u') or \
                literal.startswith('b') or \
                literal.startswith('f'):
                quotes: str = literal[1]
                content: str = literal[2:-1]
            else:
                quotes: str = literal[0]
                content: str = literal[1:-1]

            # checks for violation
            if quotes == '"':
                if "'" in content:
                    continue
                self.add_message(
                    self.MSG_SINGLE,
                    line=position.lineno,
                    col_offset=position.col_offset,
                    end_lineno=position.end_lineno,
                    end_col_offset=position.end_col_offset,
                )
                continue
            if "'" not in content:
                continue
            self.add_message(
                self.MSG_DOUBLE,
                line=position.lineno,
                col_offset=position.col_offset,
                end_lineno=position.end_lineno,
                end_col_offset=position.end_col_offset,
            )


def register(linter: 'PyLinter') -> None:
    linter.register_checker(StringQuotesChecker(linter))
