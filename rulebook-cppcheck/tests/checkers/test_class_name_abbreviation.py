from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.class_name_abbreviation import ClassNameAbbreviationChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestClassNameAbbreviationChecker(CheckerTestCase):
    CHECKER_CLASS = ClassNameAbbreviationChecker

    @patch.object(ClassNameAbbreviationChecker, 'report_error')
    def test_valid_name(self, mock_report):
        self.checker.run_check(
            self._create_configuration([
                {'type': 'Class', 'className': 'MySqlParser', 'line': 1},
            ]),
        )
        mock_report.assert_not_called()

    @patch.object(ClassNameAbbreviationChecker, 'report_error')
    def test_invalid_name(self, mock_report):
        self.checker.run_check(
            self._create_configuration([
                {'type': 'Class', 'className': 'MySQLParser', 'line': 10},
            ]),
        )
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG, 'MySqlParser'))

    def _create_configuration(self, scopes_data):
        mock_scopes = []
        for data in scopes_data:
            scope = MagicMock()
            scope.type = data['type']
            scope.className = data['className']
            body_start = MagicMock()
            name_token = MagicMock()
            body_start.linenr = data['line']
            body_start.str = '{'
            name_token.str = data['className']
            body_start.previous = name_token
            name_token.previous = None
            scope.bodyStart = body_start
            self.last_name_token = name_token
            mock_scopes.append(scope)
        configuration = MagicMock()
        configuration.scopes = mock_scopes
        return configuration


if __name__ == '__main__':
    main()
