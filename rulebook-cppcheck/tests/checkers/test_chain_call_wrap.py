from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.chain_call_wrap import ChainCallWrapChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestChainCallWrapChecker(CheckerTestCase):
    CHECKER_CLASS = ChainCallWrapChecker

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_correct_wrapping_not_reported(self, mock_report):
        obj = MagicMock(str='obj', linenr='1')
        dot1 = MagicMock(str='.', linenr='2', previous=obj)

        self.checker.process_token(dot1)
        mock_report.assert_not_called()

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_missing_newline_reported(self, mock_report):
        obj = MagicMock(str='obj', linenr='1')
        dot1 = MagicMock(str='.', linenr='1', previous=obj)
        call1 = MagicMock(str='method', linenr='1', previous=dot1)
        dot2 = MagicMock(str='.', linenr='2', previous=call1)

        dot1.next = call1
        call1.next = dot2

        self.checker.process_token(dot1)
        mock_report.assert_called_with(dot1, _Messages.get(self.checker.MSG_MISSING))


if __name__ == '__main__':
    main()
