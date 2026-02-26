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
    _MSG_MISSING: str = 'operator.wrap.missing'
    _MSG_UNEXPECTED: str = 'operator.wrap.unexpected'

    _IGNORE_TOKENS: set[str] = {'(', ')', '[', ']', '{', '}', ',', '.', '::', '?', ':'}

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        for token in [
            t for t in tokens
            if t.isOp and
               not t.isAssignmentOp and
               (t.astOperand1 is not None and t.astOperand2 is not None) and
               t.str not in self._IGNORE_TOKENS
        ]:
            if token.previous is not None and \
                token.linenr > token.previous.linenr:
                self.report_error(token, _Messages.get(self._MSG_UNEXPECTED, token.str))
                continue

            next_token: Token | None = token.next
            if next_token is None or next_token.str in {'{', '['}:
                continue

            top_node: Token | None = _parent(token, lambda t: t.isOp and not t.isAssignmentOp)
            start_token: Token | None = top_node
            while start_token.astOperand1 is not None:
                start_token = start_token.astOperand1
            end_token: Token | None = top_node
            while end_token.astOperand2 is not None:
                end_token = end_token.astOperand2

            if end_token.linenr <= start_token.linenr or next_token.linenr != token.linenr:
                continue
            self.report_error(token, _Messages.get(self._MSG_MISSING, token.str))
