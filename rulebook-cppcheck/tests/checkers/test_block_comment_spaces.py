from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.block_comment_spaces import BlockCommentSpacesChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestBlockCommentSpacesChecker(CheckerTestCase):
    CHECKER_CLASS = BlockCommentSpacesChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(BlockCommentSpacesChecker, 'report_error')
    def test_untrimmed_block_comment(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            '''
            /** Summary. */
            fun foo() {}

            /**
             * Summary.
             *
             * @param num description.
             */
            fun bar(num: Int) {}
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(BlockCommentSpacesChecker, 'report_error')
    def test_trimmed_block_comment(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            '''
            /**Summary.*/
            fun foo() {}

            /**
             *Summary.
             *
             *@param num description.
             */
            fun bar(num: Int) {}
            ''',
        )
        self.assertEqual(mock_report.call_count, 4)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG_SINGLE_START))
        self.assertEqual(calls[0][0][2], 2)
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG_SINGLE_END))
        self.assertEqual(calls[1][0][2], 2)
        self.assertEqual(calls[2][0][1], _Messages.get(self.checker.MSG_MULTI))
        self.assertEqual(calls[2][0][2], 6)
        self.assertEqual(calls[3][0][1], _Messages.get(self.checker.MSG_MULTI))
        self.assertEqual(calls[3][0][2], 8)

    @patch.object(BlockCommentSpacesChecker, 'report_error')
    def test_unconventional_block_tags(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            '''
            /**
             *Summary.
             *
             *@param num description.
             *@goodtag
             *@awesometag with description.
             */
            fun foo(num: Int) {}
            ''',
        )
        self.assertEqual(mock_report.call_count, 4)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG_MULTI))
        self.assertEqual(calls[0][0][2], 3)
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG_MULTI))
        self.assertEqual(calls[1][0][2], 5)
        self.assertEqual(calls[2][0][1], _Messages.get(self.checker.MSG_MULTI))
        self.assertEqual(calls[2][0][2], 6)
        self.assertEqual(calls[3][0][1], _Messages.get(self.checker.MSG_MULTI))
        self.assertEqual(calls[3][0][2], 7)


if __name__ == '__main__':
    main()
