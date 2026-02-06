from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.abbreviation_as_word import AbbreviationAsWordChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestAbbreviationAsWordChecker(CheckerTestCase):
    CHECKER_CLASS = AbbreviationAsWordChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(AbbreviationAsWordChecker, 'report_error')
    def test_class_names_with_lowercase_abbreviation(self, mock_report):
        self.checker.visit_scope(self._create_scope('Class', 'MySqlClass', 1))
        self.checker.visit_scope(self._create_scope('Interface', 'MySqlInterface', 3))
        self.checker.visit_scope(self._create_scope('Annotation', 'MySqlAnnotation', 5))
        self.checker.visit_scope(self._create_scope('Enum', 'MySqlEnum', 7))
        mock_report.assert_not_called()

    @patch.object(AbbreviationAsWordChecker, 'report_error')
    def test_class_names_with_uppercase_abbreviation(self, mock_report):
        for i, scope in enumerate([
            self._create_scope('Class', 'MySQLClass', 1),
            self._create_scope('Interface', 'MySQLInterface', 3),
            self._create_scope('Annotation', 'MySQLAnnotation', 5),
            self._create_scope('Enum', 'MySQLEnum', 7),
        ]):
            self.checker.visit_scope(scope)

        self.assertEqual(mock_report.call_count, 4)
        calls = mock_report.call_args_list

        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG, 'MySqlClass'))
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG, 'MySqlInterface'))
        self.assertEqual(calls[2][0][1], _Messages.get(self.checker.MSG, 'MySqlAnnotation'))
        self.assertEqual(calls[3][0][1], _Messages.get(self.checker.MSG, 'MySqlEnum'))

    @staticmethod
    def _create_scope(scope_type, class_name, line):
        scope = MagicMock()
        scope.type = scope_type
        scope.className = class_name
        body_start = MagicMock()
        name_token = MagicMock()
        body_start.linenr = line
        body_start.str = '{'
        name_token.str = class_name
        body_start.previous = name_token
        scope.bodyStart = body_start
        return scope


if __name__ == '__main__':
    main()
