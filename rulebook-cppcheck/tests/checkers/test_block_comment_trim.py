from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.block_comment_trim import BlockCommentTrimChecker
from ..tests import assert_properties, CheckerTestCase


class TestBlockCommentTrimChecker(CheckerTestCase):
    CHECKER_CLASS = BlockCommentTrimChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(BlockCommentTrimChecker, 'report_error')
    def test_block_comment_without_initial_and_final_newline(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            /**
             * Lorem ipsum.
             */
            void foo() {}
            ''',
        )
        report_error.assert_not_called()

    @patch.object(BlockCommentTrimChecker, 'report_error')
    def test_block_comment_with_initial_and_final_newline(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            /**
             *
             * Lorem ipsum.
             *
             *
             */
            void foo() {}
            ''',
        )
        self.assertEqual(report_error.call_count, 2)
        calls = report_error.call_args_list
        self.assertEqual(calls[0][0][1], "Remove blank line after '/**'.")
        self.assertEqual(calls[0][0][2], 3)
        self.assertEqual(calls[1][0][1], "Remove blank line before '*/'.")
        self.assertEqual(calls[1][0][2], 7)

    @patch.object(BlockCommentTrimChecker, 'report_error')
    def test_block_tag_description_with_final_newline(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            /**
             * @return a number.
             *
             */
            int foo() {}
            ''',
        )
        report_error.assert_called_once()
        args, _ = report_error.call_args
        self.assertEqual(args[1], "Remove blank line before '*/'.")
        self.assertEqual(args[2], 5)

    @patch.object(BlockCommentTrimChecker, 'report_error')
    def test_skip_single_line_block_comment(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            /** Lorem ipsum. */
            int foo() {}
            ''',
        )
        report_error.assert_not_called()

    @patch.object(BlockCommentTrimChecker, 'report_error')
    def test_skip_blank_block_comment(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            /**
             *
             */
            int foo() {}
            ''',
        )
        report_error.assert_not_called()

    @patch.object(BlockCommentTrimChecker, 'report_error')
    def test_skip_multiline_block_tag_description(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            /**
             * @param bar Lorem ipsum
             * dolor sit amet.
             */
            int foo(int bar) {}
            ''',
        )
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
