from unittest import TestCase, main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.indent_style import IndentStyleChecker
from rulebook_cppcheck.messages import _Messages


class TestIndentStyleChecker(TestCase):
    checker = IndentStyleChecker()

    @patch.object(IndentStyleChecker, 'report_error')
    @patch('builtins.open', new_callable=mock_open, read_data='    int x = 0;')
    def test_space_indentation(self, mock_file, mock_report):
        configuration = self._create_configuration(line_nr=1)
        self.checker.run_check(configuration)
        mock_report.assert_not_called()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')

    @patch.object(IndentStyleChecker, 'report_error')
    @patch('builtins.open', new_callable=mock_open, read_data='  int x = 0;')
    def test_tab_indentation(self, mock_file, mock_report):
        configuration = self._create_configuration(line_nr=1)
        self.checker.run_check(configuration)
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
