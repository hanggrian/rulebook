from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.assignment_wrap import AssignmentWrapChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestAssignmentWrapChecker(CheckerTestCase):
    CHECKER_CLASS = AssignmentWrapChecker

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_single_line_assignment(self, mock_report):
        assign = self._create_assign_tokens(linenr=1, rhs_linenr=1)
        self.checker.process_token(assign)
        mock_report.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_multiline_assignment_with_breaking_assignee(self, mock_report):
        assign = self._create_assign_tokens(linenr=1, rhs_linenr=2)
        self.checker.process_token(assign)
        mock_report.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_multiline_assignment_with_non_breaking_assignee(self, mock_report):
        assign = self._create_assign_tokens(linenr=1, rhs_linenr=1)
        rhs_first = assign.next
        rhs_second = MagicMock(str='+', linenr=2, next=None)
        rhs_first.next = rhs_second
        assign.astOperand2 = rhs_first
        self.checker.process_token(assign)
        mock_report.assert_called_once_with(assign, _Messages.get(self.checker.MSG))

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_multiline_variable_but_single_line_value(self, mock_report):
        assign = self._create_assign_tokens(linenr=2, rhs_linenr=2)
        self.checker.process_token(assign)
        mock_report.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_allow_comments_after_assign_operator(self, mock_report):
        assign = self._create_assign_tokens(linenr=1, rhs_linenr=3)
        self.checker.process_token(assign)
        mock_report.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_skip_lambda_initializers(self, mock_report):
        assign = self._create_assign_tokens(linenr=1, rhs_linenr=1, rhs_str='[')
        self.checker.process_token(assign)
        mock_report.assert_not_called()

    @patch.object(AssignmentWrapChecker, 'report_error')
    def test_skip_collection_initializers(self, mock_report):
        assign = self._create_assign_tokens(linenr=1, rhs_linenr=1, rhs_str='{')
        self.checker.process_token(assign)
        mock_report.assert_not_called()

    @staticmethod
    def _create_assign_tokens(linenr, rhs_linenr, rhs_str='1'):
        assign = MagicMock(str='=', isAssignmentOp=True, linenr=linenr)
        rhs = MagicMock(str=rhs_str, linenr=rhs_linenr, next=None)
        assign.next = rhs
        assignee = MagicMock(linenr=linenr, next=assign)
        assign.astOperand1 = assignee
        return assign


if __name__ == '__main__':
    main()
