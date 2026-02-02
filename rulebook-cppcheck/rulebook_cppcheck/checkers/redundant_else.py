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
    MSG: str = 'redundant.else'

    @override
    def process_token(self, token: Token) -> None:
        if token.str != 'if':
            return

        if_token: Token | None = token
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
                return
            self.report_error(else_token, _Messages.get(self.MSG))

            next_token: Token | None = else_token.next
            if next_token and next_token.str == 'if':
                if_token = next_token
            else:
                if_token = None

    @staticmethod
    def _get_else_token(if_token: Token) -> Token | None:
        curr_token: Token | None = if_token.next
        if curr_token and curr_token.str == '(':
            curr_token = curr_token.link.next
        if curr_token and curr_token.str == '{':
            curr_token = curr_token.link.next
        else:
            curr_token = \
                _next_sibling(
                    curr_token,
                    lambda t: t.str in {'else', ';'},
                )
            if curr_token and curr_token.str == ';':
                curr_token = curr_token.next
        return curr_token if curr_token and curr_token.str == 'else' else None
