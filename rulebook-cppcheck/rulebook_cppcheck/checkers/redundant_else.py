from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _has_jump_statement, _next_sibling

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class RedundantElseChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#redundant-else"""
    ID: str = 'redundant-else'
    _MSG: str = 'redundant.else'

    _ELSE_SIBLING_TOKENS: set[str] = {'else', ';'}

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        for token in [t for t in tokens if t.str == 'if']:
            if_token: Token | None = token
            continue_outer: bool = False
            while if_token:
                # skip single if
                else_token = self._get_else_token(if_token)
                if not else_token:
                    break

                # checks for violation
                then_token: Token | None = if_token.next
                if then_token and then_token.str == '(':
                    then_token = then_token.link.next
                if not _has_jump_statement(then_token, else_token):
                    continue_outer = True
                    break
                if continue_outer:
                    continue
                self.report_error(else_token, _Messages.get(self._MSG))

                next_token: Token | None = else_token.next
                if next_token and next_token.str == 'if':
                    if_token = next_token
                else:
                    if_token = None

    def _get_else_token(self, if_token: Token) -> Token | None:
        curr_token: Token | None = if_token.next
        if curr_token and curr_token.str == '(':
            curr_token = curr_token.link.next
        if curr_token and curr_token.str == '{':
            curr_token = curr_token.link.next
        else:
            curr_token = \
                _next_sibling(curr_token, lambda t: t.str in self._ELSE_SIBLING_TOKENS)
            if curr_token and curr_token.str == ';':
                curr_token = curr_token.next
        return curr_token if curr_token and curr_token.str == 'else' else None
