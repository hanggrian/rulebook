from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _is_multiline, _next_sibling

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class AssignmentWrapChecker(RulebookTokenChecker):
    ID: str = 'assignment-wrap'
    MSG: str = 'assignment.wrap'

    @override
    def process_token(self, token: Token) -> None:
        # filter operator
        if token.str != '=' or not token.isAssignmentOp:
            return

        # skip array initializer
        assignee_start: Token | None = token.next
        if not assignee_start:
            return
        if assignee_start.str in {'{', '['}:
            return

        # find multiline assignee
        assignee_end: Token | None = \
            assignee_start.astParent.astOperand2 \
                if assignee_start.astParent \
                else _next_sibling(assignee_start, lambda t: t.linenr != assignee_start.linenr)
        if not assignee_end:
            return

        # checks for violation
        if not _is_multiline(assignee_start, assignee_end) or \
            assignee_start.linenr != token.linenr:
            return
        self.report_error(token, _Messages.get(self.MSG))
