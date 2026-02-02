from unittest import main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.duplicate_blank_line_in_block_comment import \
    DuplicateBlankLineInBlockCommentChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestDuplicateBlankLineInBlockCommentChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateBlankLineInBlockCommentChecker

    @patch.object(DuplicateBlankLineInBlockCommentChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            /**
             * Lorem
             *
             * Ipsum
             */'
            ''',
    )
    def test_valid_blank_block_comments(self, mock_file, mock_report):
        token = MagicMock()
        token.file = 'test.c'
        config = MagicMock()
        config.tokenlist = [token]
        self.checker.run_check(config)
        mock_report.assert_not_called()
        mock_file.assert_called_once_with('test.c', 'r', encoding='UTF-8')

    @patch.object(DuplicateBlankLineInBlockCommentChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            /**
             * Lorem
             *
             *
             * Ipsum
             */
            ''',
    )
    def test_duplicate_blank_block_comments(self, mock_file, mock_report):
        token = MagicMock()
        token.file = 'test.c'
        config = MagicMock()
        config.tokenlist = [token]
        self.checker.run_check(config)
        mock_report.assert_called_once()
        mock_file.assert_called_once_with('test.c', 'r', encoding='UTF-8')
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))


if __name__ == '__main__':
    main()
