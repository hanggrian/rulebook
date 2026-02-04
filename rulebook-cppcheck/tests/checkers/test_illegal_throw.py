from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.illegal_throw import IllegalThrowChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestIllegalThrowChecker(CheckerTestCase):
    CHECKER_CLASS = IllegalThrowChecker

    @patch.object(IllegalThrowChecker, 'report_error')
    def test_legal_throw(self, mock_report):
        token = MagicMock(str='throw')
        exc = MagicMock(str='std::invalid_argument')
        semi = MagicMock(str=';')
        token.next = exc
        exc.next = semi
        self.checker.process_token(token)
        mock_report.assert_not_called()

    @patch.object(IllegalThrowChecker, 'report_error')
    def test_illegal_throw_violations(self, mock_report):
        token = MagicMock(str='throw')
        std = MagicMock(str='std')
        colon = MagicMock(str='::')
        exc = MagicMock(str='exception')
        semi = MagicMock(str=';')
        token.next = std
        std.next = colon
        colon.next = exc
        exc.next = semi
        self.checker.process_token(token)
        mock_report.assert_called_once()
        self.assertEqual(mock_report.call_args[0][0].str, 'exception')
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))


if __name__ == '__main__':
    main()
