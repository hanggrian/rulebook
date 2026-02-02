from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.parameter_wrap import ParameterWrapChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestParameterWrapChecker(CheckerTestCase):
    CHECKER_CLASS = ParameterWrapChecker

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_missing_newline(self, mock_report):
        lparen = MagicMock(name='lparen', str='(', linenr='1')
        p1 = MagicMock(name='p1', str='a', linenr='2')
        comma = MagicMock(name='comma', str=',', linenr='2')
        p2 = MagicMock(name='p2', str='b', linenr='2')
        p3 = MagicMock(name='p3', str='c', linenr='3')
        comma2 = MagicMock(name='comma2', str=',', linenr='2')
        rparen = MagicMock(name='rparen', str=')', linenr='4')

        lparen.next = p1
        p1.next = comma
        comma.next = p2
        p2.next = comma2
        comma2.next = p3
        p3.next = rparen
        rparen.next = None

        self.checker.run_check(MagicMock(tokenlist=[lparen, p1, comma, p2, comma2, p3, rparen]))

        mock_report.assert_called_once()
        mock_report.assert_called_once_with(p2, _Messages.get(self.checker.MSG))


if __name__ == '__main__':
    main()
