from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.operator_wrap import OperatorWrapChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestOperatorWrapChecker(CheckerTestCase):
    CHECKER_CLASS = OperatorWrapChecker

    @patch.object(OperatorWrapChecker, 'report_error')
    def test_missing_wrap(self, mock_report):
        false1 = MagicMock(name='false1', str='false', linenr=1)
        plus1 = MagicMock(name='plus1', str='+', isOp=True, isAssignmentOp=False, linenr=1)
        plus2 = MagicMock(name='plus2', str='+', isOp=True, isAssignmentOp=False, linenr=2)
        false2 = MagicMock(name='false2', str='false', linenr=1)
        true1 = MagicMock(name='true1', str='true', linenr=2)
        plus1.previous = MagicMock(linenr=1)
        plus1.next = false2
        plus1.astParent = None
        plus1.astOperand1 = false1
        plus1.astOperand2 = plus2
        plus2.previous = MagicMock(linenr=2)
        plus2.next = true1
        plus2.astParent = plus1
        plus2.astOperand1 = false2
        plus2.astOperand2 = true1
        for m in [false1, false2, true1]:
            m.astOperand1 = m.astOperand2 = None
        false1.astParent = plus1
        false2.astParent = plus2
        true1.astParent = plus2
        config = MagicMock(tokenlist=[plus1])
        self.checker.run_check(config)
        mock_report.assert_any_call(plus1, _Messages.get(self.checker.MSG_MISSING, '+'))

    @patch.object(OperatorWrapChecker, 'report_error')
    def test_unexpected_wrap(self, mock_report):
        val = MagicMock(str='0', linenr=1)
        plus = MagicMock(str='+', isOp=True, isAssignmentOp=False, linenr=2)
        rhs = MagicMock(str='1', linenr=2)
        plus.previous = val
        plus.next = rhs
        plus.astParent = None
        plus.astOperand1 = val
        plus.astOperand2 = rhs
        val.next = plus
        val.astParent = plus
        val.astOperand1 = val.astOperand2 = None
        rhs.astParent = plus
        rhs.astOperand1 = rhs.astOperand2 = None
        self.checker.run_check(MagicMock(tokenlist=[plus]))
        mock_report.assert_called_once_with(plus, _Messages.get(self.checker.MSG_UNEXPECTED, '+'))


if __name__ == '__main__':
    main()
