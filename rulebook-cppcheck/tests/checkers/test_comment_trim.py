from unittest import main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.comment_trim import CommentTrimChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestCommentTrimChecker(CheckerTestCase):
    CHECKER_CLASS = CommentTrimChecker

    @patch.object(CommentTrimChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            // Comment 1
            // Comment 2
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

    @patch.object(CommentTrimChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            //
            // Comment
            //
            ''',
    )
    def test_trim_violations(self, mock_file, mock_report):
        token = MagicMock()
        token.file = 'test.c'
        config = MagicMock()
        config.tokenlist = [token]
        self.checker.run_check(config)
        mock_report.assert_called()
        self.assertEqual(mock_report.call_count, 2)
        mock_file.assert_called_once_with('test.c', 'r', encoding='UTF-8')
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))


if __name__ == '__main__':
    main()
