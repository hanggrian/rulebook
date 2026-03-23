from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class RedundantIfChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#redundant-if"""
    ID: str = 'redundant-if'
    _MSG: str = 'redundant.if'

    _BOOLS = {'true', 'false'}

    def process_tokens(self, tokens: list[Token]) -> None:
        for token in [t for t in tokens if t.str == 'if']:
            paren: Token | None = token.next
            if paren is None or paren.str != '(':
                continue

            then_value, after_then = \
                self._read_bool_return(paren.link.next if paren.link else None)
            if then_value is None or \
                after_then is None:
                continue

            # checks for violation
            else_value, _ = \
                self._read_bool_return(after_then.next) \
                    if after_then.str == 'else' \
                    else self._read_bool_return(after_then)
            if else_value is None:
                continue
            self.report_error(token, _Messages.get(self._MSG, then_value, else_value))

    @staticmethod
    def _read_bool_return(token: Token | None) -> tuple[str | None, Token | None]:
        if token is None:
            return None, None
        braced: bool = token.str == '{'
        if braced:
            token = token.next
        if token is None or token.str != 'return':
            return None, None
        value: Token | None = token.next
        if value is None or value.str not in RedundantIfChecker._BOOLS:
            return None, None
        semi: Token | None = value.next
        if semi is None or semi.str != ';':
            return None, None
        after: Token | None = semi.next
        if braced:
            if after is None or after.str != '}':
                return None, None
            after = after.next
        return value.str, after
