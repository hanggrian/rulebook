from unittest import main
from unittest.mock import patch, call

from rulebook_cppcheck.checkers.class_name import ClassNameChecker
from ..tests import assert_properties, CheckerTestCase


class TestClassNameChecker(CheckerTestCase):
    CHECKER_CLASS = ClassNameChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ClassNameChecker, 'report_error')
    def test_valid_pascal_case(self, report_error):
        _, scopes = \
            self.dump_code(
                '''
                class MyClass {}
                struct DataNode {}
                union RawData {}
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()

    @patch.object(ClassNameChecker, 'report_error')
    def test_invalid_formats(self, report_error):
        tokens, scopes = \
            self.dump_code(
                '''
                class my_class {}
                struct data_node {}
                union raw_data {}
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'my_class'),
                    "Rename class to 'MyClass'.",
                ),
                call(
                    next(t for t in tokens if t.str == 'data_node'),
                    "Rename class to 'DataNode'.",
                ),
                call(
                    next(t for t in tokens if t.str == 'raw_data'),
                    "Rename class to 'RawData'.",
                ),
            ],
            any_order=True,
        )


if __name__ == '__main__':
    main()
