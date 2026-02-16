from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.block_tag_indentation import BlockTagIndentationChecker
from ..tests import assert_properties, CheckerTestCase


class TestBlockTagIndentationChecker(CheckerTestCase):
    CHECKER_CLASS = BlockTagIndentationChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(BlockTagIndentationChecker, 'report_error')
    def test_indented_block_tag_description(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            /**
             * @constructor lorem
             *     ipsum.
             * @param bar lorem
             *     ipsum.
             */
            class Foo(val bar: Int)
            ''',
        )
        report_error.assert_not_called()

    @patch.object(BlockTagIndentationChecker, 'report_error')
    def test_unindented_block_tag_description(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            /**
             * @constructor lorem
             *   ipsum.
             * @param bar lorem
             *    ipsum.
             */
            class Foo(val bar: Int)
            ''',
        )
        self.assertEqual(report_error.call_count, 2)
        calls = report_error.call_args_list
        self.assertEqual(calls[0][0][1], "Indent block tag description by '4 spaces'.")
        self.assertEqual(calls[0][0][2], 4)
        self.assertEqual(calls[1][0][1], "Indent block tag description by '4 spaces'.")
        self.assertEqual(calls[1][0][2], 6)


if __name__ == '__main__':
    main()
