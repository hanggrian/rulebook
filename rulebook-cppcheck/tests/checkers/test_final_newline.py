from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.final_newline import FinalNewlineChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestFinalNewlineChecker(CheckerTestCase):
    CHECKER_CLASS = FinalNewlineChecker

    @patch.object(FinalNewlineChecker, 'report_error')
    def test_file_ends_with_newline(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            'content\n',
        )
        mock_report.assert_not_called()

    @patch.object(FinalNewlineChecker, 'report_error')
    def test_file_missing_newline(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            'content',
        )
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))

    @patch.object(FinalNewlineChecker, 'report_error')
    def test_empty_file(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            '',
        )
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
