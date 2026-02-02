from unittest import main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.duplicate_blank_line import DuplicateBlankLineChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestDuplicateBlankLineChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateBlankLineChecker

    @patch.object(DuplicateBlankLineChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            int x = 0;

            int y = 0;
            ''',
    )
    def test_valid_lines(self, mock_file, mock_report):
        token = MagicMock()
        token.file = 'test.cpp'
        config = MagicMock()
        config.tokenlist = [token]
        self.checker.run_check(config)
        mock_report.assert_not_called()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')

    @patch.object(DuplicateBlankLineChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            int x = 0;


            int y = 0;
            ''',
    )
    def test_blank_lines(self, mock_file, mock_report):
        token = MagicMock()
        token.file = 'test.cpp'
        config = MagicMock()
        config.tokenlist = [token]
        self.checker.run_check(config)
        mock_report.assert_called_once()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))


if __name__ == '__main__':
    main()
