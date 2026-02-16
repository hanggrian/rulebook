from unittest import main
from unittest.mock import patch, call

from rulebook_cppcheck.checkers.package_name import PackageNameChecker
from ..tests import assert_properties, CheckerTestCase


class TestPackageNameChecker(CheckerTestCase):
    CHECKER_CLASS = PackageNameChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(PackageNameChecker, 'report_error')
    def test_valid_namespace(self, report_error):
        _, scopes = \
            self.dump_code(
                '''
                namespace my_namespace {
                    int x = 0;
                }
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()

    @patch.object(PackageNameChecker, 'report_error')
    def test_invalid_namespaces(self, report_error):
        tokens, scopes = \
            self.dump_code(
                '''
                namespace MyNamespace {
                    int x = 0;
                }
                namespace xmlParser {
                    int x = 0;
                }
                namespace UIHandler {
                    int x = 0;
                }
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'MyNamespace'),
                    "Rename package to 'my_namespace'.",
                ),
                call(
                    next(t for t in tokens if t.str == 'xmlParser'),
                    "Rename package to 'xml_parser'.",
                ),
                call(
                    next(t for t in tokens if t.str == 'UIHandler'),
                    "Rename package to 'ui_handler'.",
                ),
            ],
            any_order=True,
        )


if __name__ == '__main__':
    main()
