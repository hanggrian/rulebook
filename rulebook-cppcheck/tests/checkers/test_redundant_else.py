from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.redundant_else import RedundantElseChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestRedundantElseChecker(CheckerTestCase):
    CHECKER_CLASS = RedundantElseChecker

    @patch.object(RedundantElseChecker, 'report_error')
    def test_no_violations(self, mock_report):
        r_brace = MagicMock(str='}', next=None)
        l_brace = MagicMock(str='{', link=r_brace)
        r_paren = MagicMock(str=')', next=l_brace)
        l_paren = MagicMock(str='(', link=r_paren)
        if_token = MagicMock(str='if', next=l_paren)
        l_brace.next = r_brace
        self.checker.run_check(MagicMock(tokenlist=[if_token]))
        mock_report.assert_not_called()

    @patch.object(RedundantElseChecker, 'report_error')
    def test_redundant_else_violation(self, mock_report):
        else_body = MagicMock(str='{', next=None)
        else_token = MagicMock(str='else', next=else_body)
        return_token = MagicMock(str='return', next=else_token)
        r_paren = MagicMock(str=')', next=return_token)
        l_paren = MagicMock(str='(', link=r_paren)
        if_token = MagicMock(str='if', next=l_paren)
        self.checker.run_check(MagicMock(tokenlist=[if_token]))
        mock_report.assert_called_once()
        self.assertEqual(mock_report.call_args[0][1], _Messages.get(self.checker.MSG))


if __name__ == '__main__':
    main()
