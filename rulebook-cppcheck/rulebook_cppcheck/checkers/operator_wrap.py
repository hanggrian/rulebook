from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _parent

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class OperatorWrapChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#operator-wrap"""
    ID: str = 'operator-wrap'
    MSG_MISSING: str = 'operator.wrap.missing'
    MSG_UNEXPECTED: str = 'operator.wrap.unexpected'

    TARGET_TOKENS: set[str] = {'(', ')', '[', ']', '{', '}', ',', '.', '::', '?', ':'}

    @override
    def process_token(self, token: Token) -> None:
        if not token.isOp or \
            token.isAssignmentOp or \
            not (token.astOperand1 and token.astOperand2) or \
            token.str in self.TARGET_TOKENS:
            return

        if token.previous and token.linenr > token.previous.linenr:
            self.report_error(token, _Messages.get(self.MSG_UNEXPECTED, token.str))
            return

        next_token: Token | None = token.next
        if not next_token or next_token.str in {'{', '['}:
            return

        top_node: Token | None = _parent(token, lambda t: t.isOp and not t.isAssignmentOp)
        start_token: Token | None = top_node
        while start_token.astOperand1:
            start_token = start_token.astOperand1
        end_token: Token | None = top_node
        while end_token.astOperand2:
            end_token = end_token.astOperand2

        if end_token.linenr <= start_token.linenr or next_token.linenr != token.linenr:
            return
        self.report_error(token, _Messages.get(self.MSG_MISSING, token.str))
