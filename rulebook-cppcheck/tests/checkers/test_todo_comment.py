from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.todo_comment import TodoCommentChecker
from ..tests import CheckerTestCase, assert_properties


class TestTodoCommentChecker(CheckerTestCase):
    CHECKER_CLASS = TodoCommentChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(TodoCommentChecker, 'report_error')
    def test_uppercase_todo_comments(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            // TODO add tests
            // FIXME fix bug
            ''',
        )
        report_error.assert_not_called()

    @patch.object(TodoCommentChecker, 'report_error')
    def test_lowercase_todo_comments(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            // todo add tests
            // fixme fix bug
            ''',
        )
        self.assertEqual(report_error.call_count, 2)
        self.assertEqual(
            report_error.call_args_list[0][0][1],
            "Capitalize keyword 'todo'.",
        )
        self.assertEqual(
            report_error.call_args_list[1][0][1],
            "Capitalize keyword 'fixme'.",
        )

    @patch.object(TodoCommentChecker, 'report_error')
    def test_unknown_todo_comments(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            // TODO: add tests
            // FIXME1 fix bug
            ''',
        )
        self.assertEqual(report_error.call_count, 2)
        self.assertEqual(
            report_error.call_args_list[0][0][1],
            "Omit separator ':'.",
        )
        self.assertEqual(
            report_error.call_args_list[1][0][1],
            "Omit separator '1'.",
        )

    @patch.object(TodoCommentChecker, 'report_error')
    def test_todos_in_block_comments(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            /** todo add tests */

            /**
             * FIXME: memory leak
             */
            ''',
        )
        self.assertEqual(report_error.call_count, 2)
        self.assertEqual(
            report_error.call_args_list[0][0][1],
            "Capitalize keyword 'todo'.",
        )
        self.assertEqual(
            report_error.call_args_list[1][0][1],
            "Omit separator ':'.",
        )

    @patch.object(TodoCommentChecker, 'report_error')
    def test_todo_keyword_mid_sentence(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            // Untested. Todo: add tests.
            ''',
        )
        self.assertEqual(report_error.call_count, 2)
        self.assertEqual(
            report_error.call_args_list[0][0][1],
            "Capitalize keyword 'Todo'.",
        )
        self.assertEqual(
            report_error.call_args_list[1][0][1],
            "Omit separator ':'.",
        )


if __name__ == '__main__':
    main()
