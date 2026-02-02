from unittest import main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.todo_comment import TodoCommentChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestTodoCommentChecker(CheckerTestCase):
    CHECKER_CLASS = TodoCommentChecker

    @patch.object(TodoCommentChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            // TODO standard
            /*
             * FIXME standard
             */
            ''',
    )
    def test_no_violations(self, mock_file, mock_report):
        token = MagicMock(file='test.c')
        config = MagicMock(tokenlist=[token])
        self.checker.run_check(config)
        mock_report.assert_not_called()
        mock_file.assert_called_once_with('test.c', 'r', encoding='UTF-8')

    @patch.object(TodoCommentChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            // todo: lowercase and colon
            /*
             * fixme-dash
             */
            ''',
    )
    def test_todo_violations(self, mock_file, mock_report):
        token = MagicMock(file='test.c')
        config = MagicMock(tokenlist=[token])
        self.checker.run_check(config)
        mock_report.assert_called()
        mock_file.assert_called_once_with('test.c', 'r', encoding='UTF-8')
        self.assertEqual(mock_report.call_count, 4)
        args0, _ = mock_report.call_args_list[0]
        self.assertEqual(args0[1], _Messages.get(self.checker.MSG_KEYWORD, 'todo'))
        args1, _ = mock_report.call_args_list[1]
        self.assertEqual(args1[1], _Messages.get(self.checker.MSG_SEPARATOR, ':'))


if __name__ == '__main__':
    main()
