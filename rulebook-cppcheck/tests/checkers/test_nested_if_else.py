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
    def test_empty_or_single_statement(self, mock_report):
        scope = self._scope()
        fn_open = self._token('{', 1, scope)
        scope.bodyStart = fn_open
        if_tok, lp, rp, if_open, if_close = self._if_block(scope, 2, 2, 3)
        s, semi = self._stmt(2)
        fn_close = self._token('}', 4, scope)
        self._chain([fn_open, if_tok, lp, rp, if_open, s, semi, if_close, fn_close])
        self.checker.process_token(fn_close)
        mock_report.assert_not_called()

    @patch.object(NestedIfElseChecker, 'report_error')
    def test_invert_multiline(self, mock_report):
        scope = self._scope()
        fn_open = self._token('{', 1, scope)
        scope.bodyStart = fn_open
        if_tok, lp, rp, if_open, if_close = self._if_block(scope, 2, 2, 5)
        s1, semi1 = self._stmt(3)
        s2, semi2 = self._stmt(4)
        fn_close = self._token('}', 6, scope)
        self._chain([fn_open, if_tok, lp, rp, if_open, s1, semi1, s2, semi2, if_close, fn_close])
        self.checker.process_token(fn_close)
        mock_report.assert_called_once_with(if_tok, _Messages.get(self.checker.MSG_INVERT))

    @patch.object(NestedIfElseChecker, 'report_error')
    def test_lift_else(self, mock_report):
        scope = self._scope()
        fn_open = self._token('{', 1, scope)
        scope.bodyStart = fn_open
        if_tok, lp, rp, if_open, if_close = self._if_block(scope, 2, 2, 4)
        s1, semi1 = self._stmt(3)
        else_tok, else_open, else_close = self._else_block(4, 7)
        s2, semi2 = self._stmt(5)
        s3, semi3 = self._stmt(6)
        fn_close = self._token('}', 8, scope)
        self._chain([
            fn_open,
            if_tok,
            lp,
            rp,
            if_open,
            s1,
            semi1,
            if_close,
            else_tok,
            else_open,
            s2,
            semi2,
            s3,
            semi3,
            else_close,
            fn_close,
        ])
        self.checker.process_token(fn_close)
        mock_report.assert_called_once_with(else_tok, _Messages.get(self.checker.MSG_LIFT))

    @patch('rulebook_cppcheck.checkers.nested_if_else._has_jump_statement', return_value=False)
    @patch.object(NestedIfElseChecker, 'report_error')
    def test_skip_else_if(self, mock_report, _mock_jump):
        scope = self._scope()
        fn_open = self._token('{', 1, scope)
        scope.bodyStart = fn_open
        if_tok, lp, rp, if_open, if_close = self._if_block(scope, 2, 2, 5)
        s1, semi1 = self._stmt(3)
        s2, semi2 = self._stmt(4)
        else_tok = self._token('else', 5)
        elif_tok, elif_lp, elif_rp, elif_open, elif_close = self._if_block(scope, 5, 5, 6)
        s3, semi3 = self._stmt(5)
        fn_close = self._token('}', 7, scope)
        self._chain([
            fn_open,
            if_tok,
            lp,
            rp,
            if_open,
            s1,
            semi1,
            s2,
            semi2,
            if_close,
            else_tok,
            elif_tok,
            elif_lp,
            elif_rp,
            elif_open,
            s3,
            semi3,
            elif_close,
            fn_close,
        ])
        self.checker.process_token(fn_close)
        mock_report.assert_not_called()

    @patch('rulebook_cppcheck.checkers.nested_if_else._has_jump_statement', return_value=True)
    @patch.object(NestedIfElseChecker, 'report_error')
    def test_skip_jump_statement(self, mock_report, _mock_jump):
        scope = self._scope()
        fn_open = self._token('{', 1, scope)
        scope.bodyStart = fn_open
        if_tok, lp, rp, if_open, if_close = self._if_block(scope, 2, 2, 5)
        s, semi = self._stmt(3)
        ret = self._token('return', 4)
        ret_semi = self._token(';', 4)
        fn_close = self._token('}', 6, scope)
        self._chain([fn_open, if_tok, lp, rp, if_open, s, semi, ret, ret_semi, if_close, fn_close])
        self.checker.process_token(fn_close)
        mock_report.assert_not_called()

    @patch('rulebook_cppcheck.checkers.nested_if_else._has_jump_statement', return_value=False)
    @patch.object(NestedIfElseChecker, 'report_error')
    def test_trailing_non_ifs(self, mock_report, _mock_jump):
        scope = self._scope()
        fn_open = self._token('{', 1, scope)
        scope.bodyStart = fn_open
        if_tok, lp, rp, if_open, if_close = self._if_block(scope, 2, 2, 5)
        s1, semi1 = self._stmt(3)
        s2, semi2 = self._stmt(4)
        trailing_semi = self._token(';', 6)
        fn_close = self._token('}', 7, scope)
        self._chain([
            fn_open,
            if_tok,
            lp,
            rp,
            if_open,
            s1,
            semi1,
            s2,
            semi2,
            if_close,
            trailing_semi,
            fn_close,
        ])
        self.checker.process_token(fn_close)
        mock_report.assert_called_once_with(if_tok, _Messages.get(self.checker.MSG_INVERT))

    @patch.object(NestedIfElseChecker, 'report_error')
    def test_skip_recursive_if(self, mock_report):
        fn_scope = self._scope()
        inner_scope = self._scope()
        fn_open = self._token('{', 1, fn_scope)
        fn_scope.bodyStart = fn_open
        outer_if = self._token('if', 2, fn_scope)
        outer_lp = self._token('(', 2)
        outer_rp = self._token(')', 2)
        outer_lp.link = outer_rp
        outer_rp.link = outer_lp
        outer_open = self._token('{', 2)
        outer_close = self._token('}', 7)
        outer_open.link = outer_close
        outer_close.link = outer_open
        inner_if, inner_lp, inner_rp, inner_open, inner_close = self._if_block(inner_scope, 3, 3, 6)
        s1, semi1 = self._stmt(4)
        s2, semi2 = self._stmt(5)
        trailing_call, trailing_semi = self._stmt(8)
        fn_close = self._token('}', 9, fn_scope)
        self._chain([
            fn_open,
            outer_if,
            outer_lp,
            outer_rp,
            outer_open,
            inner_if,
            inner_lp,
            inner_rp,
            inner_open,
            s1,
            semi1,
            s2,
            semi2,
            inner_close,
            outer_close,
            trailing_call,
            trailing_semi,
            fn_close,
        ])
        self.checker.process_token(fn_close)
        mock_report.assert_not_called()

    @patch.object(NestedIfElseChecker, 'report_error')
    def test_skip_try_catch_scope(self, mock_report):
        scope = self._scope('Try')
        fn_close = self._token('}', 5, scope)
        self.checker.process_token(fn_close)
        mock_report.assert_not_called()

    @patch.object(NestedIfElseChecker, 'report_error')
    def test_skip_else_block_single_statement(self, mock_report):
        scope = self._scope()
        fn_open = self._token('{', 1, scope)
        scope.bodyStart = fn_open
        if_tok, lp, rp, if_open, if_close = self._if_block(scope, 2, 2, 4)
        s1, semi1 = self._stmt(3)
        else_tok, else_open, else_close = self._else_block(4, 5)
        s2, semi2 = self._stmt(4)
        fn_close = self._token('}', 6, scope)
        self._chain([
            fn_open,
            if_tok,
            lp,
            rp,
            if_open,
            s1,
            semi1,
            if_close,
            else_tok,
            else_open,
            s2,
            semi2,
            else_close,
            fn_close,
        ])
        self.checker.process_token(fn_close)
        mock_report.assert_not_called()

    @staticmethod
    def _token(s, linenr=1, scope=None):
        tok = MagicMock(spec=Token)
        tok.str = s
        tok.linenr = linenr
        tok.scope = scope
        return tok

    @staticmethod
    def _chain(tokens):
        for i in range(len(tokens) - 1):
            tokens[i].next = tokens[i + 1]
            tokens[i + 1].previous = tokens[i]
        tokens[0].previous = None
        tokens[-1].next = None

    @staticmethod
    def _scope(scope_type='Function'):
        scope = MagicMock()
        scope.type = scope_type
        return scope

    @staticmethod
    def _if_block(scope, if_line, open_line, close_line):
        if_tok = TestNestedIfElseChecker._token('if', if_line, scope)
        lp = TestNestedIfElseChecker._token('(', if_line)
        rp = TestNestedIfElseChecker._token(')', if_line)
        lp.link = rp
        rp.link = lp
        open_brace = TestNestedIfElseChecker._token('{', open_line)
        close_brace = TestNestedIfElseChecker._token('}', close_line)
        open_brace.link = close_brace
        close_brace.link = open_brace
        return if_tok, lp, rp, open_brace, close_brace

    @staticmethod
    def _else_block(open_line, close_line):
        else_tok = TestNestedIfElseChecker._token('else', open_line)
        open_brace = TestNestedIfElseChecker._token('{', open_line)
        close_brace = TestNestedIfElseChecker._token('}', close_line)
        open_brace.link = close_brace
        close_brace.link = open_brace
        return else_tok, open_brace, close_brace

    @staticmethod
    def _stmt(linenr=1):
        s = TestNestedIfElseChecker._token('baz', linenr)
        semi = TestNestedIfElseChecker._token(';', linenr)
        return s, semi


if __name__ == '__main__':
    main()
