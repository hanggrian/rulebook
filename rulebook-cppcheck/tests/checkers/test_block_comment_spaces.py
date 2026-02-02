from unittest import main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.block_comment_spaces import BlockCommentSpacesChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestBlockCommentSpacesChecker(CheckerTestCase):
    CHECKER_CLASS = BlockCommentSpacesChecker

    @patch.object(BlockCommentSpacesChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            /**
             * Valid description
             */
            /* Single line *
            ''',
    )
    def test_valid_comments(self, mock_file, mock_report):
        token = MagicMock()
        token.file = 'test.cpp'
        config = MagicMock()
        config.tokenlist = [token]
        self.checker.run_check(config)
        mock_report.assert_not_called()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')

    @patch.object(BlockCommentSpacesChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            /**Function description
             * @return ...
             */
            /**
             * Function description*/
            /**
             *Function description
             */
            ''',
    )
    def test_violations(self, mock_file, mock_report):
        token = MagicMock()
        token.file = 'test.cpp'
        config = MagicMock()
        config.tokenlist = [token]
        self.checker.run_check(config)
        self.assertEqual(mock_report.call_count, 3)
        calls = mock_report.call_args_list
        print('calls')
        print(calls)
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG_SINGLE_START))
        self.assertEqual(calls[0][0][2], 2)
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG_SINGLE_END))
        self.assertEqual(calls[1][0][2], 6)
        self.assertEqual(calls[2][0][1], _Messages.get(self.checker.MSG_MULTI))
        self.assertEqual(calls[2][0][2], 8)
        mock_report.assert_called()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')


if __name__ == '__main__':
    main()
