from textwrap import dedent
from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.comment_trim import CommentTrimChecker
from ..tests import CheckerTestCase, assert_properties


class TestCommentTrimChecker(CheckerTestCase):
    CHECKER_CLASS = CommentTrimChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(CommentTrimChecker, 'report_error')
    def test_comment_without_initial_and_final_newline(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                void foo() {
                    // Lorem ipsum.
                }
                ''',
            ),
        )
        report_error.assert_not_called()

    @patch.object(CommentTrimChecker, 'report_error')
    def test_comment_with_initial_and_final_newline(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                void foo() {
                    //
                    // Lorem ipsum.
                    //
                }
                ''',
            ),
        )
        self.assertEqual(report_error.call_count, 2)
        calls = report_error.call_args_list
        self.assertEqual(calls[0][0][1], "Remove blank line after '//'.")
        self.assertEqual(calls[0][0][2], 3)
        self.assertEqual(calls[1][0][1], "Remove blank line after '//'.")
        self.assertEqual(calls[1][0][2], 5)

    @patch.object(CommentTrimChecker, 'report_error')
    def test_skip_blank_comment(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                void foo() {

                    //

                }
                ''',
            ),
        )
        report_error.assert_not_called()

    @patch.object(CommentTrimChecker, 'report_error')
    def test_skip_comment_with_code(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                void foo() {
                    printf(""); //
                    printf(""); // Lorem ipsum.
                    printf(""); //
                }
                ''',
            ),
        )
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
