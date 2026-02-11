from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.todo_comment import TodoCommentChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestTodoCommentChecker(CheckerTestCase):
    CHECKER_CLASS = TodoCommentChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(TodoCommentChecker, 'report_error')
    def test_uppercase_todo_comments(self, mock_report):
        self.checker.check_file(
            self.mock_file(),
            '''
            // TODO add tests
            // FIXME fix bug
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(TodoCommentChecker, 'report_error')
    def test_lowercase_todo_comments(self, mock_report):
        self.checker.check_file(
            self.mock_file(),
            '''
            // todo add tests
            // fixme fix bug
            ''',
        )
        self.assertEqual(mock_report.call_count, 2)
        self.assertEqual(
            mock_report.call_args_list[0][0][1],
            _Messages.get(self.checker.MSG_KEYWORD, 'todo'),
        )
        self.assertEqual(
            mock_report.call_args_list[1][0][1],
            _Messages.get(self.checker.MSG_KEYWORD, 'fixme'),
        )

    @patch.object(TodoCommentChecker, 'report_error')
    def test_unknown_todo_comments(self, mock_report):
        self.checker.check_file(
            self.mock_file(),
            '''
            // TODO: add tests
            // FIXME1 fix bug
            ''',
        )
        self.assertEqual(mock_report.call_count, 2)
        self.assertEqual(
            mock_report.call_args_list[0][0][1],
            _Messages.get(self.checker.MSG_SEPARATOR, ':'),
        )
        self.assertEqual(
            mock_report.call_args_list[1][0][1],
            _Messages.get(self.checker.MSG_SEPARATOR, '1'),
        )

    @patch.object(TodoCommentChecker, 'report_error')
    def test_todos_in_block_comments(self, mock_report):
        self.checker.check_file(
            self.mock_file(),
            '''
            /** todo add tests */

            /**
             * FIXME: memory leak
             */
            ''',
        )
        self.assertEqual(mock_report.call_count, 2)
        self.assertEqual(
            mock_report.call_args_list[0][0][1],
            _Messages.get(self.checker.MSG_KEYWORD, 'todo'),
        )
        self.assertEqual(
            mock_report.call_args_list[1][0][1],
            _Messages.get(self.checker.MSG_SEPARATOR, ':'),
        )

    @patch.object(TodoCommentChecker, 'report_error')
    def test_todo_keyword_mid_sentence(self, mock_report):
        self.checker.check_file(
            self.mock_file(),
            '''
            // Untested. Todo: add tests.
            ''',
        )
        self.assertEqual(mock_report.call_count, 2)
        self.assertEqual(
            mock_report.call_args_list[0][0][1],
            _Messages.get(self.checker.MSG_KEYWORD, 'Todo'),
        )
        self.assertEqual(
            mock_report.call_args_list[1][0][1],
            _Messages.get(self.checker.MSG_SEPARATOR, ':'),
        )


if __name__ == '__main__':
    main()
