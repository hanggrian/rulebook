from textwrap import dedent
from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers import BlockCommentSpacesChecker
from ..tests import CheckerTestCase, assert_properties


class TestBlockCommentSpacesChecker(CheckerTestCase):
    CHECKER_CLASS = BlockCommentSpacesChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(BlockCommentSpacesChecker, 'report_error')
    def test_untrimmed_block_comment(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                /** Summary. */
                void foo() {}

                /**
                 * Summary.
                 *
                 * @param num description.
                 */
                void bar(int num) {}
                ''',
            ),
        )
        report_error.assert_not_called()

    @patch.object(BlockCommentSpacesChecker, 'report_error')
    def test_trimmed_block_comment(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                /**Summary.*/
                void foo() {}

                /**
                 *Summary.
                 *
                 *@param num description.
                 */
                void bar(int num) {}
                ''',
            ),
        )
        self.assertEqual(report_error.call_count, 4)
        calls = report_error.call_args_list
        self.assertEqual(calls[0][0][1], "Add space after '/**'.")
        self.assertEqual(calls[0][0][2], 2)
        self.assertEqual(calls[1][0][1], "Add space before '*/'.")
        self.assertEqual(calls[1][0][2], 2)
        self.assertEqual(calls[2][0][1], "Add space after '*'.")
        self.assertEqual(calls[2][0][2], 6)
        self.assertEqual(calls[3][0][1], "Add space after '*'.")
        self.assertEqual(calls[3][0][2], 8)

    @patch.object(BlockCommentSpacesChecker, 'report_error')
    def test_unconventional_block_tags(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                /**
                 *Summary.
                 *
                 *@param num description.
                 *@goodtag
                 *@awesometag with description.
                 */
                void foo(int num) {}
                ''',
            ),
        )
        self.assertEqual(report_error.call_count, 4)
        calls = report_error.call_args_list
        self.assertEqual(calls[0][0][1], "Add space after '*'.")
        self.assertEqual(calls[0][0][2], 3)
        self.assertEqual(calls[1][0][1], "Add space after '*'.")
        self.assertEqual(calls[1][0][2], 5)
        self.assertEqual(calls[2][0][1], "Add space after '*'.")
        self.assertEqual(calls[2][0][2], 6)
        self.assertEqual(calls[3][0][1], "Add space after '*'.")
        self.assertEqual(calls[3][0][2], 7)


if __name__ == '__main__':
    main()
