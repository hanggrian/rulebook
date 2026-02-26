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
    _MSG: str = 'assignment.wrap'

    _START_TOKENS: set[str] = {'{', '[', '(', 'lambda'}

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        # checks for violation
        for token in [t for t in tokens if t.str == '=' and t.isAssignmentOp]:
            rhs_start: Token | None = token.next
            if rhs_start is None or rhs_start.str in self._START_TOKENS:
                continue
            rhs_end: Token | None = token.astOperand2
            if rhs_end is None:
                continue
            last_rhs_token: Token = rhs_end
            while last_rhs_token.astOperand2 is not None:
                last_rhs_token = last_rhs_token.astOperand2
            if last_rhs_token.str == ';':
                last_rhs_token = last_rhs_token.previous

            if rhs_start.linenr != token.linenr or last_rhs_token.linenr <= token.linenr:
                continue
            self.report_error(token, _Messages.get(self._MSG))
