from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


class MemberSeparatorChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#member-separator"""
    ID: str = 'member-separator'
    MSG: str = 'member.separator'

    @override
    def get_scope_set(self) -> set[str]:
        return {'Class', 'Struct'}

    @override
    def visit_scope(self, scope: Scope) -> None:
        # get member end token
        members: list[tuple[Token, Token, bool]] = []
        curr: Token | None = scope.bodyStart.next
        while curr and curr is not scope.bodyEnd:
            if curr.scope is scope and (curr.variable or curr.function):
                is_var: bool = curr.variable is not None
                start_token: Token = curr
                end_token: Token = curr
                if not is_var and curr.function and curr.function.tokenDef:
                    search: Token | None = curr.function.tokenDef
                    while search and search.str not in ('{', ';') and search is not scope.bodyEnd:
                        search = search.next
                    if search and search.str == '{' and search.link:
                        end_token = search.link
                    elif search and search.str == ';':
                        end_token = search
                else:
                    search: Token | None = curr
                    while search and search.str != ';' and search.next and search.next.scope is scope:
                        search = search.next
                    if search and search.str == ';':
                        end_token = search
                members.append((start_token, end_token, is_var))
                curr = end_token.next
                continue
            curr = curr.next

        # checks for violation
        for i in range(1, len(members)):
            prev_start, prev_end, prev_is_var = members[i - 1]
            curr_start, _, curr_is_var = members[i]
            if prev_is_var and curr_is_var:
                continue
            if curr_start.linenr - prev_end.linenr >= 2:
                continue
            key: str = 'property' if prev_is_var else 'function'
            if not prev_is_var and prev_start.str == scope.className:
                key = 'constructor'
            self.report_error(prev_end, _Messages.get(self.MSG, key))
