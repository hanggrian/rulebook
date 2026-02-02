from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.class_name import ClassNameChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestClassNameChecker(CheckerTestCase):
    CHECKER_CLASS = ClassNameChecker

    @patch.object(ClassNameChecker, 'report_error')
    def test_valid_pascal_case(self, mock_report):
        self.checker.run_check(
            self._create_configuration([
                {'type': 'Class', 'className': 'MyClass'},
                {'type': 'Struct', 'className': 'DataNode'},
            ]),
        )
        mock_report.assert_not_called()

    @patch.object(ClassNameChecker, 'report_error')
    def test_invalid_formats(self, mock_report):
        self.checker.run_check(
            self._create_configuration([
                {'type': 'Class', 'className': 'my_class'},
                {'type': 'Struct', 'className': 'data_node'},
                {'type': 'Union', 'className': 'raw_data'},
                {'type': 'Enum', 'className': 'color_type'},
                {'type': 'Class', 'className': 'xmlParser'},
            ]),
        )
        mock_report.assert_called()
        self.assertEqual(mock_report.call_count, 5)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG, 'MyClass'))
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG, 'DataNode'))
        self.assertEqual(calls[2][0][1], _Messages.get(self.checker.MSG, 'RawData'))
        self.assertEqual(calls[3][0][1], _Messages.get(self.checker.MSG, 'ColorType'))
        self.assertEqual(calls[4][0][1], _Messages.get(self.checker.MSG, 'XmlParser'))

    @staticmethod
    def _create_configuration(scopes_data):
        mock_scopes = []
        for data in scopes_data:
            scope = MagicMock()
            scope.type = data['type']
            scope.className = data['className']
            body_start = MagicMock()
            name_token = MagicMock()
            body_start.str = '{'
            name_token.str = data['className']
            body_start.previous = name_token
            name_token.previous = None
            scope.bodyStart = body_start
            mock_scopes.append(scope)
        configuration = MagicMock()
        configuration.scopes = mock_scopes
        return configuration


if __name__ == '__main__':
    main()
