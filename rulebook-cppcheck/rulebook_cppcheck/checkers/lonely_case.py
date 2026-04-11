from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import Messages
from rulebook_cppcheck.nodes import prev_sibling

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


class LonelyCaseChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#lonely-case"""
    ID: str = 'lonely-case'
    _MSG: str = 'lonely.case'

    def get_scopeset(self) -> set[str]:
        return {'Switch'}

    def visit_scope(self, scope: Scope) -> None:
        # checks for violation
        case_count: int = 0
        curr_token: Token | None = scope.bodyStart
        while curr_token is not None and \
            curr_token is not scope.bodyEnd:
            if not isinstance(curr_token, Token):
                continue
            if curr_token.str == 'case':
                case_count += 1
            curr_token = curr_token.next
        if case_count > 1:
            return
        switch_token: Token | None = prev_sibling(scope.bodyStart, lambda t: t.str == 'switch')
        if switch_token is None:
            return
        self.report_error(switch_token, Messages.get(self._MSG))
