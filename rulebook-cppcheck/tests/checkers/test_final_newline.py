from unittest import main
from unittest.mock import MagicMock, mock_open, patch

from rulebook_cppcheck.checkers.final_newline import FinalNewlineChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestFinalNewlineChecker(CheckerTestCase):
    CHECKER_CLASS = FinalNewlineChecker

    @patch.object(FinalNewlineChecker, 'report_error')
    @patch('builtins.open', new_callable=mock_open, read_data='content\n')
    def test_file_ends_with_newline(self, mock_file, mock_report):
        self.checker.run_check(self._create_configuration(['token'], 'test.cpp'))
        mock_report.assert_not_called()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')

    @patch.object(FinalNewlineChecker, 'report_error')
    @patch('builtins.open', new_callable=mock_open, read_data='content')
    def test_file_missing_newline(self, mock_file, mock_report):
        self.checker.run_check(self._create_configuration(['token'], 'test.cpp'))
        mock_report.assert_called_once()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))

    @patch.object(FinalNewlineChecker, 'report_error')
    @patch('builtins.open', new_callable=mock_open, read_data='')
    def test_empty_file(self, mock_file, mock_report):
        self.checker.run_check(self._create_configuration(['token'], 'test.cpp'))
        mock_report.assert_not_called()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')

    @patch.object(FinalNewlineChecker, 'report_error')
    @patch('builtins.open', new_callable=mock_open, read_data='content')
    def test_multiple_files_only_last_token_per_file(self, mock_file, mock_report):
        configuration = \
            self._create_configuration(
                ['token1', 'token2', 'token3'],
                ['file1.cpp', 'file2.cpp', 'file1.cpp'],
            )
        self.checker.run_check(configuration)
        mock_report.assert_called()
        self.assertEqual(mock_report.call_count, 2)
        self.assertEqual(mock_file.call_count, 2)

    @staticmethod
    def _create_configuration(code_tokens, files=None):
        if files is None:
            files = ['test.cpp'] * len(code_tokens)
        elif isinstance(files, str):
            files = [files] * len(code_tokens)
        tokens = [MagicMock(str=s, file=f) for s, f in zip(code_tokens, files)]
        configuration = MagicMock()
        configuration.tokenlist = tokens
        return configuration


if __name__ == '__main__':
    main()
