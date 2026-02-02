from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.redundant_default import RedundantDefaultChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestRedundantDefaultChecker(CheckerTestCase):
    CHECKER_CLASS = RedundantDefaultChecker

    @patch.object(RedundantDefaultChecker, 'report_error')
    def test_no_violations(self, mock_report):
        r_brace = MagicMock(str='}', next=None)
        default_token = MagicMock(str='default', next=MagicMock(str=':', next=r_brace))
        case_token = MagicMock(str='case', next=MagicMock(str=':', next=default_token))
        l_brace = MagicMock(str='{', link=r_brace, next=case_token)
        r_paren = MagicMock(str=')', next=l_brace)
        l_paren = MagicMock(str='(', link=r_paren)
        switch_token = MagicMock(str='switch', next=l_paren)
        self.checker.run_check(MagicMock(tokenlist=[switch_token]))
        mock_report.assert_not_called()

    @patch.object(RedundantDefaultChecker, 'report_error')
    def test_redundant_default_violation(self, mock_report):
        r_brace = MagicMock(str='}', next=None)
        default_token = MagicMock(str='default', next=MagicMock(str=':', next=r_brace))
        break_token = MagicMock(str='break', next=default_token)
        case_token = MagicMock(str='case', next=MagicMock(str=':', next=break_token))
        l_brace = MagicMock(str='{', link=r_brace, next=case_token)
        r_paren = MagicMock(str=')', next=l_brace)
        l_paren = MagicMock(str='(', link=r_paren)
        switch_token = MagicMock(str='switch', next=l_paren)
        self.checker.run_check(MagicMock(tokenlist=[switch_token]))
        mock_report.assert_called_once()
        self.assertEqual(mock_report.call_args[0][1], _Messages.get(self.checker.MSG))


if __name__ == '__main__':
    main()
