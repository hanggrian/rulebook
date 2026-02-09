from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _has_jump_statement, _prev_sibling, _next_sibling

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


class NestedIfElseChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#nested-if-else"""
    ID: str = 'nested-if-else'
    MSG_INVERT: str = 'nested.if.else.invert'
    MSG_LIFT: str = 'nested.if.else.lift'

    @override
    def process_token(self, token: Token) -> None:
        # start from right brace
        if token.str != '}':
            return

        # skip blocks without exit path
        scope: Scope | None = token.scope
        if not scope or scope.type in {'Try', 'Catch'}:
            return

        # get last if
        last_if: Token | None = self._find_last_if_in_block(token)
        if not last_if:
            return

        # checks for violation
        else_token: Token | None = self._get_else_token(last_if)
        if else_token:
            if else_token.next and else_token.next.str == 'if':
                return
            if self._is_multiline_block(else_token.next):
                self.report_error(else_token, _Messages.get(self.MSG_LIFT))
            return
        then_block: Token | None = self._get_then_block_start(last_if)
        if self._has_jump(then_block) or not self._is_multiline_block(then_block):
            return
        self.report_error(last_if, _Messages.get(self.MSG_INVERT))

    def _find_last_if_in_block(self, r_brace: Token) -> Token | None:
        prev_sibling: Token | None = _prev_sibling(r_brace.previous, lambda t: t.str != ';')
        if not prev_sibling:
            return None
        search: Token | None = prev_sibling
        if search.str == '}' and search.link:
            search = search.link.previous
        while search and search is not r_brace.scope.bodyStart:
            if search.str == 'if' and search.scope is r_brace.scope:
                if self._get_construct_end(search) is prev_sibling:
                    return search
            if search.str == '}' and search.link:
                search = search.link.previous
                continue
            if search.str in {';', '{'}:
                break
            search = search.previous
        return None

    def _get_else_token(self, if_token: Token) -> Token | None:
        body_end: Token | None = self._get_body_end(if_token)
        if body_end and body_end.next and body_end.next.str == 'else':
            return body_end.next
        return None

    def _get_construct_end(self, if_token: Token) -> Token | None:
        curr_token: Token | None = if_token
        while True:
            body_end = self._get_body_end(curr_token)
            if not body_end:
                return None
            next_tok = body_end.next
            if next_tok and next_tok.str == 'else':
                if next_tok.next and next_tok.next.str == 'if':
                    curr_token = next_tok.next
                    continue
                return self._get_body_end(next_tok)
            return body_end

    @staticmethod
    def _get_body_end(tok: Token) -> Token | None:
        curr_token: Token | None = tok.next
        if curr_token and curr_token.str == '(':
            curr_token = curr_token.link.next if curr_token.link else None
        if not curr_token:
            return None
        if curr_token.str == '{':
            return curr_token.link
        return _next_sibling(curr_token, lambda t: t.str == ';')

    @staticmethod
    def _get_then_block_start(if_token: Token) -> Token | None:
        curr_token: Token | None = if_token.next
        if curr_token and curr_token.str == '(':
            return curr_token.link.next if curr_token.link else None
        return curr_token

    @staticmethod
    def _is_multiline_block(block_start: Token | None) -> bool:
        if not block_start or block_start.str != '{' or not block_start.link:
            return False
        return block_start.link.linenr > block_start.linenr + 1

    @staticmethod
    def _has_jump(start: Token | None) -> bool:
        if not start or start.str != '{':
            return False
        return _has_jump_statement(start, start.link)
