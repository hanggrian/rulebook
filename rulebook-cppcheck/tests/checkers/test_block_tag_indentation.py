from unittest import main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.block_tag_indentation import BlockTagIndentationChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestBlockTagIndentationChecker(CheckerTestCase):
    CHECKER_CLASS = BlockTagIndentationChecker

    @patch.object(BlockTagIndentationChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            /**
             * @param p1
             *     valid
             */
            ''',
    )
    def test_valid_indentation(self, mock_file, mock_report):
        token = MagicMock()
        token.file = 'test.c'
        config = MagicMock()
        config.tokenlist = [token]
        self.checker.run_check(config)
        mock_report.assert_not_called()
        mock_file.assert_called_once_with('test.c', 'r', encoding='UTF-8')

    @patch.object(BlockTagIndentationChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            /**
             * @param p1
             *   invalid
             */
            ''',
    )
    def test_invalid_indentation(self, mock_file, mock_report):
        token = MagicMock()
        token.file = 'test.c'
        config = MagicMock()
        config.tokenlist = [token]
        self.checker.run_check(config)
        mock_report.assert_called_once()
        mock_file.assert_called_once_with('test.c', 'r', encoding='UTF-8')
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))


if __name__ == '__main__':
    main()
