from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.operator_wrap import OperatorWrapChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestOperatorWrapChecker(CheckerTestCase):
    CHECKER_CLASS = OperatorWrapChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(OperatorWrapChecker, 'report_error')
    def test_operators_in_single_line_statement(self, mock_report):
        self.checker.process_token(self._create_op_mock('+', 1, 1, 1))
        mock_report.assert_not_called()

    @patch.object(OperatorWrapChecker, 'report_error')
    def test_nl_wrapped_operators_in_multi_line_statement(self, mock_report):
        plus = self._create_op_mock('+', 3, 2, 3)
        self.checker.process_token(plus)
        mock_report.assert_called_once_with(plus, _Messages.get(self.checker.MSG_UNEXPECTED, '+'))

    @patch.object(OperatorWrapChecker, 'report_error')
    def test_eol_wrapped_operators_in_multi_line_statement(self, mock_report):
        self.checker.process_token(self._create_op_mock('+', 2, 2, 3))
        mock_report.assert_not_called()

    @patch.object(OperatorWrapChecker, 'report_error')
    def test_multiline_operand_need_to_be_wrapped(self, mock_report):
        val1 = self._create_token_mock('1', 2)
        plus = self._create_token_mock('+', 2)
        func_start = self._create_token_mock('Math.min', 2)
        func_end = self._create_token_mock(')', 4)
        plus.isOp = True
        plus.isAssignmentOp = False
        plus.astOperand1 = val1
        plus.astOperand2 = func_start
        plus.previous = val1
        plus.next = func_start
        plus.astParent = None
        val1.astParent = plus
        func_start.astParent = plus
        func_start.link = func_end
        leaf = self._create_token_mock('leaf', 4)
        func_start.astOperand2 = leaf
        self.checker.process_token(plus)
        mock_report.assert_called_once_with(plus, _Messages.get(self.checker.MSG_MISSING, '+'))

    @staticmethod
    def _create_token_mock(s, line):
        t = MagicMock(str=s, linenr=line, next=None, previous=None, link=None)
        t.astOperand1 = None
        t.astOperand2 = None
        t.astParent = None
        t.isOp = False
        return t

    @staticmethod
    def _create_op_mock(op_str, op_line, prev_line, next_line):
        lhs = TestOperatorWrapChecker._create_token_mock('lhs', prev_line)
        op = TestOperatorWrapChecker._create_token_mock(op_str, op_line)
        rhs = TestOperatorWrapChecker._create_token_mock('rhs', next_line)
        op.isOp = True
        op.isAssignmentOp = False
        op.astOperand1 = lhs
        op.astOperand2 = rhs
        op.previous = lhs
        op.next = rhs
        op.astParent = None
        lhs.astParent = op
        rhs.astParent = op
        lhs.next = op
        rhs.previous = op
        return op


if __name__ == '__main__':
    main()
