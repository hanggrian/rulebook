from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.assignment_wrap import AssignmentWrapChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestAssignmentWrapChecker(CheckerTestCase):
    CHECKER_CLASS = AssignmentWrapChecker

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_no_violations(self, mock_report):
        assign = MagicMock(str='=', isAssignmentOp=True, linenr=1)
        rhs = MagicMock(str='{', linenr=1)
        assign.next = rhs
        config = MagicMock(tokenlist=[assign])
        self.checker.run_check(config)
        mock_report.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_assignment_wrap_violation(self, mock_report):
        assign = MagicMock(str='=', isAssignmentOp=True, linenr=1)
        rhs = MagicMock(str='foo', linenr=1)
        rhs_cont = MagicMock(str='+', linenr=2)
        assign.next = rhs
        rhs.next = rhs_cont
        rhs.astParent = None
        config = MagicMock(tokenlist=[assign])
        self.checker.run_check(config)
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))


if __name__ == '__main__':
    main()
