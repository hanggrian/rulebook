from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.chain_call_wrap import ChainCallWrapChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestChainCallWrapChecker(CheckerTestCase):
    CHECKER_CLASS = ChainCallWrapChecker

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_unexpected_newline(self, mock_report):
        t_open = MagicMock(str='(', linenr=2, previous=None)
        t_close = MagicMock(str=')', linenr=3, previous=t_open)
        token1 = MagicMock(str='.', linenr=4, previous=t_close)
        token2 = MagicMock(str='.', linenr=5, previous=None, next=MagicMock(str=';', next=None))
        token1.next = token2
        self.checker.process_token(token1)
        mock_report.assert_called_once_with(token1, _Messages.get(self.checker.MSG_UNEXPECTED))

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_inconsistent_dot_position(self, mock_report):
        t_prev = MagicMock(str=')', linenr=2, previous=None)
        token1 = MagicMock(str='.', linenr=2, previous=t_prev)
        token2 = MagicMock(str='.', linenr=3, previous=None, next=MagicMock(str=';', next=None))
        token1.next = token2
        self.checker.process_token(token1)
        mock_report.assert_called_once_with(token1, _Messages.get(self.checker.MSG_MISSING))

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_allow_single_line_dots(self, mock_report):
        token1 = MagicMock(str='.', linenr=2, previous=None)
        token2 = MagicMock(str='.', linenr=2, previous=None, next=MagicMock(str=';', next=None))
        token1.next = token2
        self.checker.process_token(token1)
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
