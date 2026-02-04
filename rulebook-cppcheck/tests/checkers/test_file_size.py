from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.file_size import FileSizeChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestFileSizeChecker(CheckerTestCase):
    CHECKER_CLASS = FileSizeChecker

    @patch.object(FileSizeChecker, 'report_error')
    def test_small_file(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            'int x = 0;',
        )
        mock_report.assert_not_called()

    @patch.object(FileSizeChecker, 'report_error')
    def test_large_file(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            '// a\n' * 1001,
        )
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG, 1000))


if __name__ == '__main__':
    main()
