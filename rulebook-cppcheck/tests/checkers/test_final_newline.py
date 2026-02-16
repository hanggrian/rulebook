from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.final_newline import FinalNewlineChecker
from ..tests import assert_properties, CheckerTestCase


class TestFinalNewlineChecker(CheckerTestCase):
    CHECKER_CLASS = FinalNewlineChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(FinalNewlineChecker, 'report_error')
    def test_file_ends_with_newline(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            'content\n',
        )
        report_error.assert_not_called()

    @patch.object(FinalNewlineChecker, 'report_error')
    def test_file_missing_newline(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            'content',
        )
        report_error.assert_called_once()
        args, _ = report_error.call_args
        self.assertEqual(args[1], 'Put a blank line at the end of the file.')

    @patch.object(FinalNewlineChecker, 'report_error')
    def test_empty_file(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '',
        )
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
