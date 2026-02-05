from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.import_order import ImportOrderChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestImportOrderChecker(CheckerTestCase):
    CHECKER_CLASS = ImportOrderChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ImportOrderChecker, 'report_error')
    def test_valid_order(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            '''
            #include <numeric>
            #include <string>
            #include "abc.h"
            ''',
        )
        mock_report.assert_not_called()

    @patch.object(ImportOrderChecker, 'report_error')
    def test_invalid_sort(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            '''
            #include <string>
            #include <numeric>
            ''',
        )
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG_SORT, 'numeric', 'string'))

    @patch.object(ImportOrderChecker, 'report_error')
    def test_invalid_group(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            '''
            #include "abc.h"
            #include <numeric>
            ''',
        )
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG_SORT, 'numeric', 'abc.h'))

    @patch.object(ImportOrderChecker, 'report_error')
    def test_invalid_join(self, mock_report):
        self.checker.check_file(
            MagicMock(file='test.cpp'),
            '''
            #include <numeric>

            #include <string>
            ''',
        )
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG_JOIN, 'string'))

    @staticmethod
    def _create_configuration():
        token = MagicMock()
        token.file = 'test.cpp'
        configuration = MagicMock()
        configuration.tokenlist = [token]
        return configuration


if __name__ == '__main__':
    main()
