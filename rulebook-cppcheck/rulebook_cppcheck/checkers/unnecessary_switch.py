from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _prev_sibling

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


class UnnecessarySwitchChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#unnecessary-switch"""
    ID: str = 'unnecessary-switch'
    MSG: str = 'unnecessary.switch'

    @override
    def get_scope_set(self) -> set[str]:
        return {'Switch'}

    @override
    def visit_scope(self, scope: Scope) -> None:
        case_count: int = 0
        curr_token: Token | None = scope.bodyStart
        while curr_token and curr_token is not scope.bodyEnd:
            if curr_token.str == 'case':
                case_count += 1
            curr_token = curr_token.next
        if case_count > 1:
            return
        switch_token: Token | None = _prev_sibling(scope.bodyStart, lambda t: t.str == 'switch')
        if not switch_token:
            return
        self.report_error(switch_token, _Messages.get(self.MSG))
