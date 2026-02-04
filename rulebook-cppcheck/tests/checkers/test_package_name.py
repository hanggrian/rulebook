from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.package_name import PackageNameChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestPackageNameChecker(CheckerTestCase):
    CHECKER_CLASS = PackageNameChecker

    @patch.object(PackageNameChecker, 'report_error')
    def test_valid_namespace(self, mock_report):
        self.checker.visit_scope(self._create_scope('my_namespace'))
        mock_report.assert_not_called()

    @patch.object(PackageNameChecker, 'report_error')
    def test_invalid_namespaces(self, mock_report):
        self.checker.visit_scope(self._create_scope('MyNamespace'))
        self.checker.visit_scope(self._create_scope('xmlParser'))
        self.checker.visit_scope(self._create_scope('UIHandler'))
        mock_report.assert_called()
        self.assertEqual(mock_report.call_count, 3)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG, 'my_namespace'))
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG, 'xml_parser'))
        self.assertEqual(calls[2][0][1], _Messages.get(self.checker.MSG, 'ui_handler'))

    @staticmethod
    def _create_scope(class_name):
        scope = MagicMock(type='Namespace', className=class_name)
        body_start = MagicMock(str='{')
        name_token = MagicMock(str=class_name)
        scope.bodyStart = body_start
        body_start.previous = name_token
        return scope


if __name__ == '__main__':
    main()
