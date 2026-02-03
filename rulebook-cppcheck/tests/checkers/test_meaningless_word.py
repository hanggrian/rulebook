from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.meaningless_word import MeaninglessWordChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestMeaninglessWordChecker(CheckerTestCase):
    CHECKER_CLASS = MeaninglessWordChecker

    @patch.object(MeaninglessWordChecker, 'report_error')
    def test_valid_class_names(self, mock_report):
        self.checker.visit_scope(self._create_scope_mock('MyClass'))
        self.checker.visit_scope(self._create_scope_mock('UserProcessor'))
        self.checker.visit_scope(self._create_scope_mock('OrderService'))

        mock_report.assert_not_called()

    @patch.object(MeaninglessWordChecker, 'report_error')
    def test_invalid_class_name_suffixes(self, mock_report):
        for class_name, suffix in {
            ('StringHelper', 'Helper'),
            ('ConnectionManager', 'Manager'),
            ('DataWrapper', 'Wrapper'),
        }:
            mock_report.reset_mock()
            scope = self._create_scope_mock(class_name)
            self.checker.visit_scope(scope)

            mock_report.assert_called_once()
            args, _ = mock_report.call_args
            self.assertEqual(args[0], scope.bodyStart.previous)
            self.assertEqual(args[1], _Messages.get(self.checker.MSG, suffix))

    @patch.object(MeaninglessWordChecker, 'report_error')
    def test_utility_suffixes(self, mock_report):
        for class_name in {'MathUtil', 'NetworkUtility'}:
            mock_report.reset_mock()
            suffix = 'Util' if 'Util' in class_name and 'Utility' not in class_name else 'Utility'
            scope = self._create_scope_mock(class_name)
            self.checker.visit_scope(scope)

            mock_report.assert_called_once()
            args, _ = mock_report.call_args
            self.assertEqual(args[0], scope.bodyStart.previous)
            self.assertEqual(args[1], _Messages.get(self.checker.MSG, suffix))

    @staticmethod
    def _create_scope_mock(class_name):
        scope = MagicMock()
        scope.className = class_name
        body_start = MagicMock()
        name_token = MagicMock()
        body_start.str = '{'
        name_token.str = class_name
        body_start.previous = name_token
        name_token.previous = None
        scope.bodyStart = body_start
        return scope


if __name__ == '__main__':
    main()
