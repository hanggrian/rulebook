from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class LonelyIfChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#lonely-if"""
    ID: str = 'lonely-if'
    _MSG: str = 'lonely.if'

    def process_tokens(self, tokens: list[Token]) -> None:
        for token in tokens:
            # checks for violation
            if token.str != 'else' or \
                token.next is None or \
                token.next.str != '{':
                continue
            inner_if: Token | None = self._sole_if_in_block(token.next)
            if inner_if is None:
                return
            self.report_error(inner_if, Messages.get(self._MSG))

    @staticmethod
    def _skip_statement(token: Token | None) -> Token | None:
        if token is None:
            return None
        if token.str == '{':
            return token.link.next if token.link else None
        if token.str == 'if':
            paren: Token | None = token.next
            if paren is None or paren.str != '(':
                return None
            token = LonelyIfChecker._skip_statement(paren.link.next)
            if token and token.str == 'else':
                token = LonelyIfChecker._skip_statement(token.next)
            return token
        while token is not None and token.str != ';':
            token = token.next
        return token.next if token else None

    @staticmethod
    def _sole_if_in_block(open_brace: Token) -> Token | None:
        first: Token | None = open_brace.next
        if first is None or first.str != 'if':
            return None
        if open_brace.linenr == first.linenr:
            return None
        return first if LonelyIfChecker._skip_statement(first) is open_brace.link else None
