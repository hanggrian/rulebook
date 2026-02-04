from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.class_name import ClassNameChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestClassNameChecker(CheckerTestCase):
    CHECKER_CLASS = ClassNameChecker

    @patch.object(ClassNameChecker, 'report_error')
    def test_valid_pascal_case(self, mock_report):
        self.checker.visit_scope(self._create_scope('Class', 'MyClass'))
        self.checker.visit_scope(self._create_scope('Struct', 'DataNode'))
        mock_report.assert_not_called()

    @patch.object(ClassNameChecker, 'report_error')
    def test_invalid_formats(self, mock_report):
        self.checker.visit_scope(self._create_scope('Class', 'my_class'))
        self.checker.visit_scope(self._create_scope('Struct', 'data_node'))
        self.checker.visit_scope(self._create_scope('Union', 'raw_data'))
        self.checker.visit_scope(self._create_scope('Enum', 'color_type'))
        self.checker.visit_scope(self._create_scope('Class', 'xmlParser'))

        self.assertEqual(mock_report.call_count, 5)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG, 'MyClass'))
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG, 'DataNode'))
        self.assertEqual(calls[2][0][1], _Messages.get(self.checker.MSG, 'RawData'))
        self.assertEqual(calls[3][0][1], _Messages.get(self.checker.MSG, 'ColorType'))
        self.assertEqual(calls[4][0][1], _Messages.get(self.checker.MSG, 'XmlParser'))

    @staticmethod
    def _create_scope(scope_type, class_name):
        scope = MagicMock()
        scope.type = scope_type
        scope.className = class_name
        body_start = MagicMock()
        name_token = MagicMock()
        body_start.str = '{'
        name_token.str = class_name
        body_start.previous = name_token
        scope.bodyStart = body_start
        return scope


if __name__ == '__main__':
    main()
