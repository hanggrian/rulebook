from unittest import main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.block_tag_punctuation import BlockTagPunctuationChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestBlockTagPunctuationChecker(CheckerTestCase):
    CHECKER_CLASS = BlockTagPunctuationChecker

    @patch.object(BlockTagPunctuationChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            /**
             * @param p Parameter description!
             * @return Return description.
             */
            ''',
    )
    def test_no_violations(self, mock_file, mock_report):
        token = MagicMock(file='test.c')
        config = MagicMock(tokenlist=[token])
        self.checker.before_run({'block-tags': '@param,@return'})
        self.checker.run_check(config)
        mock_report.assert_not_called()
        mock_file.assert_called_once_with('test.c', 'r', encoding='UTF-8')

    @patch.object(BlockTagPunctuationChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            /**
             * @param p Parameter
             * @return Return
             */
            ''',
    )
    def test_block_tag_punctuation_violations(self, mock_file, mock_report):
        token = MagicMock(file='test.c')
        config = MagicMock(tokenlist=[token])
        self.checker.before_run({'block-tags': '@param,@return'})
        self.checker.run_check(config)
        mock_report.assert_called()
        self.assertEqual(
            mock_report.call_args_list[0][0][1],
            _Messages.get(self.checker.MSG, '@param'),
        )
        self.assertEqual(
            mock_report.call_args_list[1][0][1],
            _Messages.get(self.checker.MSG, '@return'),
        )
        mock_file.assert_called_once_with('test.c', 'r', encoding='UTF-8')


if __name__ == '__main__':
    main()
