from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _next_sibling

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class RedundantElseChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#redundant-else"""
    ID: str = 'redundant-else'
    _MSG: str = 'redundant.else'

    _JUMP_TOKENS: set[str] = {'return', 'break', 'continue', 'throw', 'goto'}
    _ELSE_SIBLING_TOKENS: set[str] = {'else', ';'}

    def process_tokens(self, tokens: list[Token]) -> None:
        for token in [t for t in tokens if t.str == 'if']:
            if_token: Token | None = token
            while if_token is not None:
                # skip single if
                else_token: Token | None = self._get_else_token(if_token)
                if else_token is None:
                    break

                # checks for violation
                then_token: Token | None = if_token.next
                if then_token is not None and \
                    then_token.str == '(':
                    then_token = then_token.link.next
                if not self._has_toplevel_jump(then_token):
                    break
                self.report_error(else_token, _Messages.get(self._MSG))

                next_token: Token | None = else_token.next
                if next_token is not None and \
                    next_token.str == 'if':
                    if_token = next_token
                else:
                    if_token = None

    @staticmethod
    def _has_toplevel_jump(block_token: Token | None) -> bool:
        if block_token is None or block_token.str != '{':
            return block_token is not None and \
                block_token.str in RedundantElseChecker._JUMP_TOKENS
        tok: Token | None = block_token.next
        close: Token | None = block_token.link
        last_jump: bool = False
        while tok is not None and tok is not close:
            if tok.str in RedundantElseChecker._JUMP_TOKENS:
                last_jump = True
            elif tok.str == '{':
                last_jump = False
                tok = tok.link  # jump over nested block entirely
            elif tok.str == ';':
                if not last_jump:
                    last_jump = False  # keep last_jump state accurate per-statement
            if tok is not None:
                tok = tok.next
        return last_jump

    @staticmethod
    def _get_else_token(if_token: Token) -> Token | None:
        curr_token: Token | None = if_token.next
        if curr_token is not None and \
            curr_token.str == '(':
            curr_token = curr_token.link.next
        if curr_token is not None and \
            curr_token.str == '{':
            curr_token = curr_token.link.next
        else:
            curr_token = \
                _next_sibling(
                    curr_token,
                    lambda t: t.str in RedundantElseChecker._ELSE_SIBLING_TOKENS,
                )
            if curr_token is not None and \
                curr_token.str == ';':
                curr_token = curr_token.next
        return curr_token \
            if curr_token is not None and curr_token.str == 'else' \
            else None
