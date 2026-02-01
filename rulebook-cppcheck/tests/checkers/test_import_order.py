from unittest import main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.import_order import ImportOrderChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestImportOrderChecker(CheckerTestCase):
    CHECKER_CLASS = ImportOrderChecker

    @patch.object(ImportOrderChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            #include <numeric>
            #include <string>
            #include "abc.h"
            ''',
    )
    def test_valid_order(self, mock_file, mock_report):
        self.checker.run_check(self._create_configuration())
        mock_report.assert_not_called()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')

    @patch.object(ImportOrderChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            #include <string>
            #include <numeric>
            ''',
    )
    def test_invalid_sort(self, mock_file, mock_report):
        self.checker.run_check(self._create_configuration())
        mock_report.assert_called_once()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG_SORT, 'numeric', 'string'))

    @patch.object(ImportOrderChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            #include "abc.h"
            #include <numeric>
            ''',
    )
    def test_invalid_group(self, mock_file, mock_report):
        self.checker.run_check(self._create_configuration())
        mock_report.assert_called_once()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG_SORT, 'numeric', 'abc.h'))

    @patch.object(ImportOrderChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            #include <numeric>

            #include <string>
            ''',
    )
    def test_invalid_join(self, mock_file, mock_report):
        self.checker.run_check(self._create_configuration())
        mock_report.assert_called_once()
        mock_file.assert_called_once_with('test.cpp', 'r', encoding='UTF-8')
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
