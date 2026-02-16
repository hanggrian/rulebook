from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.abbreviation_as_word import AbbreviationAsWordChecker
from ..tests import assert_properties, CheckerTestCase


class TestAbbreviationAsWordChecker(CheckerTestCase):
    CHECKER_CLASS = AbbreviationAsWordChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(AbbreviationAsWordChecker, 'report_error')
    def test_class_names_with_lowercase_abbreviation(self, report_error):
        _, scopes = \
            self.dump_code(
                '''
                class MySqlClass {}
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()

    @patch.object(AbbreviationAsWordChecker, 'report_error')
    def test_class_names_with_uppercase_abbreviation(self, report_error):
        tokens, scopes = \
            self.dump_code(
                '''
                class MySQLClass {}
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'MySQLClass'),
            "Rename abbreviation to 'MySqlClass'.",
        )


if __name__ == '__main__':
    main()
