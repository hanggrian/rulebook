from textwrap import dedent
from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.block_tag_punctuation import BlockTagPunctuationChecker
from ..tests import CheckerTestCase, assert_properties


class TestBlockTagPunctuationChecker(CheckerTestCase):
    CHECKER_CLASS = BlockTagPunctuationChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(BlockTagPunctuationChecker, 'report_error')
    def test_no_description(self, report_error):
        self.checker.before_run({
            'punctuate-block-tags': '@constructor,@receiver,@property,@param,@return',
        })
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                /**
                 * @param num
                 * @return
                 */
                int add(int num) {}
                ''',
            ),
        )
        report_error.assert_not_called()

    @patch.object(BlockTagPunctuationChecker, 'report_error')
    def test_descriptions_end_with_a_period(self, report_error):
        self.checker.before_run({
            'punctuate-block-tags': '@constructor,@receiver,@property,@param,@return',
        })
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                /**
                 * @param num value.
                 * @return total value.
                 */
                int add(int num) {}
                ''',
            ),
        )
        report_error.assert_not_called()

    @patch.object(BlockTagPunctuationChecker, 'report_error')
    def test_descriptions_end_without_a_period(self, report_error):
        self.checker.before_run({
            'punctuate-block-tags': '@constructor,@receiver,@property,@param,@return',
        })
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                /**
                 * @param num value
                 * @return total value
                 */
                int add(int num) {}
                ''',
            ),
        )
        self.assertEqual(report_error.call_count, 2)
        calls = report_error.call_args_list
        self.assertEqual(calls[0][0][1], "End '@param' with a period.")
        self.assertEqual(calls[0][0][2], 2)
        self.assertEqual(calls[1][0][1], "End '@return' with a period.")
        self.assertEqual(calls[1][0][2], 3)

    @patch.object(BlockTagPunctuationChecker, 'report_error')
    def test_long_descriptions(self, report_error):
        self.checker.before_run({
            'punctuate-block-tags': '@constructor,@receiver,@property,@param,@return',
        })
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                /**
                 * @param num
                 *     value
                 * @return total
                 *     value
                 */
                int add(int num) {}
                ''',
            ),
        )
        self.assertEqual(report_error.call_count, 2)
        calls = report_error.call_args_list
        self.assertEqual(calls[0][0][1], "End '@param' with a period.")
        self.assertEqual(calls[0][0][2], 3)
        self.assertEqual(calls[1][0][1], "End '@return' with a period.")
        self.assertEqual(calls[1][0][2], 5)


if __name__ == '__main__':
    main()
