from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.package_name import PackageNameChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestPackageNameChecker(CheckerTestCase):
    CHECKER_CLASS = PackageNameChecker

    @patch.object(PackageNameChecker, 'report_error')
    def test_valid_namespace(self, mock_report):
        configuration = self._create_configuration([{'className': 'my_namespace'}])
        self.checker.run_check(configuration)
        mock_report.assert_not_called()

    @patch.object(PackageNameChecker, 'report_error')
    def test_invalid_namespaces(self, mock_report):
        self.checker.run_check(
            self._create_configuration([
                {'className': 'MyNamespace'},
            ]),
        )
        self.checker.run_check(
            self._create_configuration([
                {'className': 'xmlParser'},
                {'className': 'UIHandler'},
            ]),
        )
        self.assertEqual(mock_report.call_count, 3)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG, 'my_namespace'))
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG, 'xml_parser'))
        self.assertEqual(calls[2][0][1], _Messages.get(self.checker.MSG, 'ui_handler'))

    @staticmethod
    def _create_configuration(scopes_data):
        mock_scopes = []
        for data in scopes_data:
            scope = MagicMock()
            scope.type = 'Namespace'
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
