from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.block_tag_indentation import BlockTagIndentationChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestBlockTagIndentationChecker(CheckerTestCase):
    CHECKER_CLASS = BlockTagIndentationChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(BlockTagIndentationChecker, 'report_error')
    def test_indented_block_tag_description(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
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
        mock_report.assert_not_called()

    @patch.object(BlockTagIndentationChecker, 'report_error')
    def test_unindented_block_tag_description(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
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
        self.assertEqual(mock_report.call_count, 2)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG))
        self.assertEqual(calls[0][0][2], 4)
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG))
        self.assertEqual(calls[1][0][2], 6)


if __name__ == '__main__':
    main()
