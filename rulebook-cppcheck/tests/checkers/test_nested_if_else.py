from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.nested_if_else import NestedIfElseChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class TestNestedIfElseChecker(CheckerTestCase):
    CHECKER_CLASS = NestedIfElseChecker

    @patch.object(NestedIfElseChecker, 'report_error')
    def test_invert_violation(self, mock_report):
        scope = MagicMock(name='scope')
        scope.type = 'Function'
        scope.bodyStart = MagicMock()
        if_tok = self.create_token('if_token', 'if', 1)
        l_paren = self.create_token('l_paren', '(', 1)
        r_paren = self.create_token('r_paren', ')', 1)
        l_brace = self.create_token('l_brace', '{', 2)
        r_brace_inner = self.create_token('r_brace_inner', '}', 5)
        r_brace_outer = self.create_token('r_brace_outer', '}', 6)
        if_tok.scope = scope
        r_brace_outer.scope = scope
        if_tok.next = l_paren
        l_paren.link = r_paren
        r_paren.next = l_brace
        l_brace.link = r_brace_inner
        l_brace.next = r_brace_inner
        r_brace_inner.next = r_brace_outer
        r_brace_outer.previous = r_brace_inner
        r_brace_inner.previous = l_brace
        r_brace_inner.link = l_brace
        l_brace.previous = r_paren
        r_paren.previous = l_paren
        l_paren.previous = if_tok
        self.checker.run_check(MagicMock(tokenlist=[r_brace_outer]))
        mock_report.assert_called_once_with(if_tok, _Messages.get(self.checker.MSG_INVERT))

    @patch.object(NestedIfElseChecker, 'report_error')
    def test_lift_violation(self, mock_report):
        scope = MagicMock(name='scope')
        scope.type = 'Function'
        scope.bodyStart = MagicMock()
        if_token = self.create_token('if_token', 'if', 1)
        l_paren = self.create_token('l_paren', '(', 1)
        r_paren = self.create_token('r_paren', ')', 1)
        if_l_brace = self.create_token('if_l', '{', 2)
        if_r_brace = self.create_token('if_r', '}', 2)
        else_token = self.create_token('else_token', 'else', 3)
        else_l_brace = self.create_token('else_l', '{', 10)
        else_r_brace = self.create_token('else_r', '}', 13)
        r_brace_outer = self.create_token('outer_r', '}', 14)
        if_token.scope = scope
        else_token.scope = scope
        r_brace_outer.scope = scope
        if_token.next = l_paren
        l_paren.link = r_paren
        r_paren.next = if_l_brace
        if_l_brace.link = if_r_brace
        if_l_brace.next = if_r_brace
        if_r_brace.next = else_token
        else_token.next = else_l_brace
        else_l_brace.link = else_r_brace
        else_l_brace.next = else_r_brace
        else_r_brace.next = r_brace_outer
        r_brace_outer.previous = else_r_brace
        else_r_brace.previous = else_l_brace
        else_r_brace.link = else_l_brace
        else_l_brace.previous = else_token
        else_token.previous = if_r_brace
        if_r_brace.previous = if_l_brace
        if_r_brace.link = if_l_brace
        if_l_brace.previous = r_paren
        r_paren.previous = l_paren
        l_paren.previous = if_token
        self.checker.run_check(MagicMock(tokenlist=[r_brace_outer]))
        mock_report.assert_called_once_with(else_token, _Messages.get(self.checker.MSG_LIFT))

    @staticmethod
    def create_token(name, string, linenr=1):
        token = MagicMock(spec=Token, name=name)
        token.str = string
        token.linenr = linenr
        token.next = None
        token.previous = None
        token.link = None
        token.scope = None
        return token


if __name__ == '__main__':
    main()
