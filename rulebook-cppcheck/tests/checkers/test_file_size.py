from unittest import TestCase, main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.file_size import FileSizeChecker
from rulebook_cppcheck.messages import _Messages


class TestFileSizeChecker(TestCase):
    checker = FileSizeChecker()

    @patch.object(FileSizeChecker, 'report_error')
    def test_valid_length(self, mock_report):
        configuration = self._create_configuration(['token'], 'test.cpp')
        with patch('builtins.open', mock_open(read_data='int x = 0;')):
            self.checker.run_check(configuration)
        mock_report.assert_not_called()

    @patch.object(FileSizeChecker, 'report_error')
    def test_invalid_length(self, mock_report):
        configuration = self._create_configuration(['token'], 'test.cpp')
        long_lines = '// a\n' * 1001
        with patch('builtins.open', mock_open(read_data=long_lines)):
            self.checker.run_check(configuration)
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG, 1000))

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
