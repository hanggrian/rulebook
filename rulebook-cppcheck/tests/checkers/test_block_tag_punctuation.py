from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.block_tag_punctuation import BlockTagPunctuationChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestBlockTagPunctuationChecker(CheckerTestCase):
    CHECKER_CLASS = BlockTagPunctuationChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(BlockTagPunctuationChecker, 'report_error')
    def test_no_description(self, mock_report):
        self.checker.before_run({
            'block-tags': '@constructor,@receiver,@property,@param,@return',
        })
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            /**
             * @param num
             * @return
             */
            int add(int num) {}
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(BlockTagPunctuationChecker, 'report_error')
    def test_descriptions_end_with_a_period(self, mock_report):
        self.checker.before_run({
            'block-tags': '@constructor,@receiver,@property,@param,@return',
        })
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            /**
             * @param num value.
             * @return total value.
             */
            int add(int num) {}
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(BlockTagPunctuationChecker, 'report_error')
    def test_descriptions_end_without_a_period(self, mock_report):
        self.checker.before_run({
            'block-tags': '@constructor,@receiver,@property,@param,@return',
        })
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            /**
             * @param num value
             * @return total value
             */
            int add(int num) {}
            ''',
        )
        self.assertEqual(mock_report.call_count, 2)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG, '@param'))
        self.assertEqual(calls[0][0][2], 2)
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG, '@return'))
        self.assertEqual(calls[1][0][2], 3)

    @patch.object(BlockTagPunctuationChecker, 'report_error')
    def test_long_descriptions(self, mock_report):
        self.checker.before_run({
            'block-tags': '@constructor,@receiver,@property,@param,@return',
        })
        self.checker.check_file(
            MagicMock(file='test.c'),
            '''
            /**
             * @param num
             *     value
             * @return total
             *     value
             */
            int add(int num) {}
            ''',
        )
        self.assertEqual(mock_report.call_count, 2)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG, '@param'))
        self.assertEqual(calls[0][0][2], 3)
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG, '@return'))
        self.assertEqual(calls[1][0][2], 5)


if __name__ == '__main__':
    main()
