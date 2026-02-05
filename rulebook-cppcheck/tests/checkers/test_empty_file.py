from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.empty_file import EmptyFileChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestEmptyFileChecker(CheckerTestCase):
    CHECKER_CLASS = EmptyFileChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(EmptyFileChecker, 'report_error')
    def test_non_empty_file(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            '// comment',
        )
        mock_report.assert_not_called()

    @patch.object(EmptyFileChecker, 'report_error')
    def test_empty_file(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            '\n',
        )
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))

    @patch.object(EmptyFileChecker, 'report_error')
    def test_long_empty_file(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            '''

            ''',
        )
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))


if __name__ == '__main__':
    main()
