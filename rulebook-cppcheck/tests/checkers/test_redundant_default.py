from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.redundant_default import RedundantDefaultChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestRedundantDefaultChecker(CheckerTestCase):
    CHECKER_CLASS = RedundantDefaultChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(RedundantDefaultChecker, 'report_error')
    def test_no_throw_or_return_in_case(self, mock_report):
        switch_token = self._create_token('switch')
        l_paren = self._create_token('(')
        r_paren = self._create_token(')')
        l_brace = self._create_token('{')
        case0 = self._create_token('case')
        colon0 = self._create_token(':')
        baz0 = self._create_token('baz')
        case1 = self._create_token('case')
        colon1 = self._create_token(':')
        baz1 = self._create_token('baz')
        default_token = self._create_token('default')
        colon_def = self._create_token(':')
        baz_def = self._create_token('baz')
        r_brace = self._create_token('}')
        switch_token.next = l_paren
        l_paren.link = r_paren
        r_paren.next = l_brace
        l_brace.link = r_brace
        l_brace.next = case0
        case0.next = colon0
        colon0.next = baz0
        baz0.next = case1
        case1.next = colon1
        colon1.next = baz1
        baz1.next = default_token
        default_token.next = colon_def
        colon_def.next = baz_def
        baz_def.next = r_brace
        self.checker.process_token(switch_token)
        mock_report.assert_not_called()

    @patch.object(RedundantDefaultChecker, 'report_error')
    def test_lift_else_when_case_has_return(self, mock_report):
        switch_token = self._create_token('switch')
        l_paren = self._create_token('(')
        r_paren = self._create_token(')')
        l_brace = self._create_token('{')
        case0 = self._create_token('case')
        colon0 = self._create_token(':')
        throw_token = self._create_token('throw')
        case1 = self._create_token('case')
        colon1 = self._create_token(':')
        return_token = self._create_token('return')
        default_token = self._create_token('default')
        colon_def = self._create_token(':')
        baz_def = self._create_token('baz')
        r_brace = self._create_token('}')
        switch_token.next = l_paren
        l_paren.link = r_paren
        r_paren.next = l_brace
        l_brace.link = r_brace
        l_brace.next = case0
        case0.next = colon0
        colon0.next = throw_token
        throw_token.next = case1
        case1.next = colon1
        colon1.next = return_token
        return_token.next = default_token
        default_token.next = colon_def
        colon_def.next = baz_def
        baz_def.next = r_brace
        self.checker.process_token(switch_token)
        mock_report.assert_called_once_with(colon_def, _Messages.get(self.checker.MSG))

    @patch.object(RedundantDefaultChecker, 'report_error')
    def test_skip_if_not_all_case_blocks_have_jump_statement(self, mock_report):
        switch_token = self._create_token('switch')
        l_paren = self._create_token('(')
        r_paren = self._create_token(')')
        l_brace = self._create_token('{')
        case0 = self._create_token('case')
        colon0 = self._create_token(':')
        return_token = self._create_token('return')
        case1 = self._create_token('case')
        colon1 = self._create_token(':')
        baz_token = self._create_token('baz')
        default_token = self._create_token('default')
        colon_def = self._create_token(':')
        baz_def = self._create_token('baz')
        r_brace = self._create_token('}')
        switch_token.next = l_paren
        l_paren.link = r_paren
        r_paren.next = l_brace
        l_brace.link = r_brace
        l_brace.next = case0
        case0.next = colon0
        colon0.next = return_token
        return_token.next = case1
        case1.next = colon1
        colon1.next = baz_token
        baz_token.next = default_token
        default_token.next = colon_def
        colon_def.next = baz_def
        baz_def.next = r_brace
        self.checker.process_token(switch_token)
        mock_report.assert_not_called()

    @staticmethod
    def _create_token(s):
        return MagicMock(str=s)


if __name__ == '__main__':
    main()
