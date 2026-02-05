from unittest import main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.line_length import LineLengthChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestLineLengthChecker(CheckerTestCase):
    CHECKER_CLASS = LineLengthChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(LineLengthChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data='int foo = 0;',
    )
    def test_valid_length(self, mock_file, mock_report):
        self.checker.process_token(self._create_token(line_nr=1))
        mock_report.assert_not_called()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')

    @patch.object(LineLengthChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data='a' * 101,
    )
    def test_invalid_length(self, mock_file, mock_report):
        self.checker.process_token(self._create_token(line_nr=1))
        mock_report.assert_called_once()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG, 100))

    @staticmethod
    def _create_token(line_nr):
        token = MagicMock()
        token.file = 'test.cpp'
        token.linenr = line_nr
        return token


if __name__ == '__main__':
    main()
