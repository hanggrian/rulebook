from unittest import TestCase, main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.line_length import LineLengthChecker
from rulebook_cppcheck.messages import _Messages


class TestLineLengthChecker(TestCase):
    checker = LineLengthChecker()

    @patch.object(LineLengthChecker, 'report_error')
    def test_valid_length(self, mock_report):
        configuration = self._create_configuration(line_nr=1)
        with patch('builtins.open', mock_open(read_data='int x = 0;')):
            self.checker.run_check(configuration)
        mock_report.assert_not_called()

    @patch.object(LineLengthChecker, 'report_error')
    def test_invalid_length(self, mock_report):
        configuration = self._create_configuration(line_nr=1)
        long_line = 'a' * 101
        with patch('builtins.open', mock_open(read_data=long_line)):
            self.checker.run_check(configuration)
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG, 100))

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
