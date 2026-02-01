from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages

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
        curr: Token | None = scope.bodyStart
        while curr and curr != scope.bodyEnd:
            if curr.str == 'case':
                case_count += 1
            curr = curr.next
        if case_count > 1:
            return
        switch_token: Token = scope.bodyStart
        while switch_token and switch_token.str != 'switch':
            switch_token = switch_token.previous
        if not switch_token:
            return
        self.report_error(switch_token, _Messages.get(self.MSG))
