from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.unnecessary_switch import UnnecessarySwitchChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestUnnecessarySwitchChecker(CheckerTestCase):
    CHECKER_CLASS = UnnecessarySwitchChecker

    @patch.object(UnnecessarySwitchChecker, 'report_error')
    def test_multiple_switch_branches(self, mock_report):
        switch_token = self._create_token('switch')
        l_brace = self._create_token('{')
        case0 = self._create_token('case')
        case1 = self._create_token('case')
        r_brace = self._create_token('}')
        switch_token.next = l_brace
        l_brace.previous = switch_token
        l_brace.next = case0
        case0.next = case1
        case1.next = r_brace
        self.checker.visit_scope(self._create_scope(l_brace, r_brace))
        mock_report.assert_not_called()

    @patch.object(UnnecessarySwitchChecker, 'report_error')
    def test_single_switch_branch(self, mock_report):
        switch_token = self._create_token('switch')
        l_brace = self._create_token('{')
        case0 = self._create_token('case')
        r_brace = self._create_token('}')
        switch_token.next = l_brace
        l_brace.previous = switch_token
        l_brace.next = case0
        case0.next = r_brace
        self.checker.visit_scope(self._create_scope(l_brace, r_brace))
        mock_report.assert_called_once_with(switch_token, _Messages.get(self.checker.MSG))

    @patch.object(UnnecessarySwitchChecker, 'report_error')
    def test_skip_single_branch_if_it_has_fall_through_condition(self, mock_report):
        switch_token = self._create_token('switch')
        l_brace = self._create_token('{')
        case0 = self._create_token('case')
        case1 = self._create_token('case')
        r_brace = self._create_token('}')
        switch_token.next = l_brace
        l_brace.previous = switch_token
        l_brace.next = case0
        case0.next = case1
        case1.next = r_brace
        self.checker.visit_scope(self._create_scope(l_brace, r_brace))
        mock_report.assert_not_called()

    @staticmethod
    def _create_token(s):
        token = MagicMock(str=s)
        return token

    @staticmethod
    def _create_scope(start, end):
        scope = MagicMock()
        scope.bodyStart = start
        scope.bodyEnd = end
        return scope


if __name__ == '__main__':
    main()
