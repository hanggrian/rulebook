from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.block_comment_trim import BlockCommentTrimChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestBlockCommentTrimChecker(CheckerTestCase):
    CHECKER_CLASS = BlockCommentTrimChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(BlockCommentTrimChecker, 'report_error')
    def test_block_comment_without_initial_and_final_newline(self, mock_report):
        self.checker.check_file(
            self.mock_file(),
            '''
            /**
             * Lorem ipsum.
             */
            void foo() {}
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(BlockCommentTrimChecker, 'report_error')
    def test_block_comment_with_initial_and_final_newline(self, mock_report):
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
        self.assertEqual(mock_report.call_count, 2)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG_FIRST))
        self.assertEqual(calls[0][0][2], 3)
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG_LAST))
        self.assertEqual(calls[1][0][2], 7)

    @patch.object(BlockCommentTrimChecker, 'report_error')
    def test_block_tag_description_with_final_newline(self, mock_report):
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
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG_LAST))
        self.assertEqual(args[2], 5)

    @patch.object(BlockCommentTrimChecker, 'report_error')
    def test_skip_single_line_block_comment(self, mock_report):
        self.checker.check_file(
            self.mock_file(),
            '''
            /** Lorem ipsum. */
            int foo() {}
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(BlockCommentTrimChecker, 'report_error')
    def test_skip_blank_block_comment(self, mock_report):
        self.checker.check_file(
            self.mock_file(),
            '''
            /**
             *
             */
            int foo() {}
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(BlockCommentTrimChecker, 'report_error')
    def test_skip_multiline_block_tag_description(self, mock_report):
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
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
