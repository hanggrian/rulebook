from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class AssignmentWrapChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#assignment-wrap"""
    ID: str = 'assignment-wrap'
    MSG: str = 'assignment.wrap'

    START_TOKENS: set[str] = {'{', '[', '(', 'lambda'}

    @override
    def process_token(self, token: Token) -> None:
        # filter operator
        if token.str != '=' or not token.isAssignmentOp:
            return

        rhs_start: Token | None = token.next
        if not rhs_start or rhs_start.str in self.START_TOKENS:
            return
        rhs_end: Token | None = token.astOperand2
        if not rhs_end:
            return
        last_rhs_token: Token = rhs_end
        while last_rhs_token.astOperand2:
            last_rhs_token = last_rhs_token.astOperand2
        if last_rhs_token.str == ';':
            last_rhs_token = last_rhs_token.previous

        # checks for violation
        if rhs_start.linenr != token.linenr or last_rhs_token.linenr <= token.linenr:
            return
        self.report_error(token, _Messages.get(self.MSG))
