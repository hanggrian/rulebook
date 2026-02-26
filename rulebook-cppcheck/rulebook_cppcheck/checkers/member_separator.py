from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _next_sibling

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


# TODO implement logic for test_capture_members_with_comments
class MemberSeparatorChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#member-separator"""
    ID: str = 'member-separator'
    _MSG: str = 'member.separator'

    _OPENING_TOKENS: set[str] = {'{', ';'}

    @override
    def get_scopeset(self) -> set[str]:
        return {'Class', 'Struct'}

    @override
    def visit_scope(self, scope: Scope) -> None:
        # get member end token
        members: list[tuple[Token, Token, bool]] = []
        body_start: Token | None = scope.bodyStart
        body_end: Token | None = scope.bodyEnd
        if body_start is None or body_end is None:
            return
        curr_token: Token | None = body_start.next
        while curr_token is not None and curr_token is not body_end:
            if curr_token.scope is scope and (curr_token.variable or curr_token.function):
                is_var: bool = curr_token.variable is not None
                start_token: Token = curr_token
                end_token: Token = curr_token
                if not is_var and curr_token.function and curr_token.function.tokenDef:
                    search = \
                        _next_sibling(
                            curr_token.function.tokenDef,
                            lambda t: t.str in self._OPENING_TOKENS or t is body_end,
                        )
                    if search and search.str == '{' and search.link:
                        end_token = search.link
                    elif search and search.str == ';':
                        end_token = search
                else:
                    search = \
                        _next_sibling(
                            curr_token,
                            lambda t: \
                                t.str == ';' or \
                                not t.next or \
                                t.next.scope is not scope,
                        )
                    if search:
                        end_token = search
                members.append((start_token, end_token, is_var))
                curr_token = end_token.next
                continue
            curr_token = curr_token.next

        # checks for violation
        for i in range(1, len(members)):
            prev_start, prev_end, prev_is_var = members[i - 1]
            current_start, _, curr_is_var = members[i]
            if prev_is_var and curr_is_var:
                continue
            if current_start.linenr - prev_end.linenr >= 2:
                continue
            msg_arg: str = 'property' if prev_is_var else 'function'
            if not prev_is_var and prev_start.str == scope.className:
                msg_arg = 'constructor'
            self.report_error(prev_end, _Messages.get(self._MSG, msg_arg))
