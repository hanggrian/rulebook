from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.empty_file import EmptyFileChecker
from ..tests import CheckerTestCase, assert_properties


class TestEmptyFileChecker(CheckerTestCase):
    CHECKER_CLASS = EmptyFileChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(EmptyFileChecker, 'report_error')
    def test_non_empty_file(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '// comment',
        )
        report_error.assert_not_called()

    @patch.object(EmptyFileChecker, 'report_error')
    def test_empty_file(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '\n',
        )
        report_error.assert_called_once()
        args, _ = report_error.call_args
        self.assertEqual(args[1], 'Delete the empty file.')

    @patch.object(EmptyFileChecker, 'report_error')
    def test_long_empty_file(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''

            ''',
        )
        report_error.assert_called_once()
        args, _ = report_error.call_args
        self.assertEqual(args[1], 'Delete the empty file.')


if __name__ == '__main__':
    main()
