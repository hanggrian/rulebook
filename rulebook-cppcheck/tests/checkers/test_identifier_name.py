from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.identifier_name import IdentifierNameChecker
from ..tests import assert_properties, CheckerTestCase


class TestIdentifierNameChecker(CheckerTestCase):
    CHECKER_CLASS = IdentifierNameChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(IdentifierNameChecker, 'report_error')
    def test_valid_names(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                int valid_variable = 0;
                void valid_function() {}
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(IdentifierNameChecker, 'report_error')
    def test_invalid_variable_name(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    int invalidVariable = 0;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'invalidVariable'),
            "Rename identifier to 'invalid_variable'.",
        )

    @patch.object(IdentifierNameChecker, 'report_error')
    def test_invalid_function_name(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void InvalidFunction() {}
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'InvalidFunction'),
            "Rename identifier to 'invalid_function'.",
        )


if __name__ == '__main__':
    main()
