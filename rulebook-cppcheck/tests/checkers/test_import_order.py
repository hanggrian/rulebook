from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.import_order import ImportOrderChecker
from ..tests import assert_properties, CheckerTestCase


class TestImportOrderChecker(CheckerTestCase):
    CHECKER_CLASS = ImportOrderChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ImportOrderChecker, 'report_error')
    def test_valid_order(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            #include <numeric>
            #include <string>
            #include "abc.h"
            ''',
        )
        report_error.assert_not_called()

    @patch.object(ImportOrderChecker, 'report_error')
    def test_invalid_sort(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            #include <string>
            #include <numeric>
            ''',
        )
        report_error.assert_called_once()
        args, _ = report_error.call_args
        self.assertEqual(args[1], "Arrange directive 'numeric' before 'string'.")

    @patch.object(ImportOrderChecker, 'report_error')
    def test_invalid_group(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            #include "abc.h"
            #include <numeric>
            ''',
        )
        report_error.assert_called_once()
        args, _ = report_error.call_args
        self.assertEqual(args[1], "Arrange directive 'numeric' before 'abc.h'.")

    @patch.object(ImportOrderChecker, 'report_error')
    def test_invalid_join(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            '''
            #include <numeric>

            #include <string>
            ''',
        )
        report_error.assert_called_once()
        args, _ = report_error.call_args
        self.assertEqual(args[1], "Remove blank line before directive 'string'.")

    @staticmethod
    def _create_configuration():
        token = MagicMock()
        token.file = 'test.cpp'
        configuration = MagicMock()
        configuration.tokenlist = [token]
        return configuration


if __name__ == '__main__':
    main()
