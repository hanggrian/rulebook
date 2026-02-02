from unittest import main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.block_comment_trim import BlockCommentTrimChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestBlockCommentTrimChecker(CheckerTestCase):
    CHECKER_CLASS = BlockCommentTrimChecker

    @patch.object(BlockCommentTrimChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            /**
             * Lorem ipsum
             */
            ''',
    )
    def test_no_violations(self, mock_file, mock_report):
        token = MagicMock()
        token.file = 'test.c'
        config = MagicMock()
        config.tokenlist = [token]
        self.checker.run_check(config)
        mock_report.assert_not_called()
        mock_file.assert_called_once_with('test.c', 'r', encoding='UTF-8')

    @patch.object(BlockCommentTrimChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            int main() {
                /**
                 *
                 * Lorem ipsum
                 *
                 *
                 */
                return 0;
            }
            ''',
    )
    def test_trim_violations(self, mock_file, mock_report):
        token = MagicMock()
        token.file = 'test.c'
        config = MagicMock()
        config.tokenlist = [token]
        self.checker.run_check(config)
        self.assertEqual(mock_report.call_count, 2)
        mock_report.assert_called()
        mock_file.assert_called_once_with('test.c', 'r', encoding='UTF-8')
        self.assertEqual(
            mock_report.call_args_list[0][0][1],
            _Messages.get(self.checker.MSG_FIRST),
        )
        self.assertEqual(
            mock_report.call_args_list[1][0][1],
            _Messages.get(self.checker.MSG_LAST),
        )


if __name__ == '__main__':
    main()
