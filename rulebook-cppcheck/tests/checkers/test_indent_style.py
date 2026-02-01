from unittest import main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.indent_style import IndentStyleChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestIndentStyleChecker(CheckerTestCase):
    CHECKER_CLASS = IndentStyleChecker

    @patch.object(IndentStyleChecker, 'report_error')
    @patch('builtins.open', new_callable=mock_open, read_data='    int x = 0;')
    def test_valid_indentation(self, mock_file, mock_report):
        self.checker.run_check(self._create_configuration(line_nr=1))
        mock_report.assert_not_called()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')

    @patch.object(IndentStyleChecker, 'report_error')
    @patch('builtins.open', new_callable=mock_open, read_data='  int x = 0;')
    def test_invalid_indentation(self, mock_file, mock_report):
        self.checker.run_check(self._create_configuration(line_nr=1))
        mock_report.assert_called_once()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG, 4))

    @staticmethod
    def _create_configuration(line_nr):
        token = MagicMock()
        token.file = 'test.cpp'
        token.linenr = line_nr
        configuration = MagicMock()
        configuration.tokenlist = [token]
        return configuration


if __name__ == '__main__':
    main()
