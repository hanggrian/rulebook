from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.identifier_name import IdentifierNameChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestIdentifierNameChecker(CheckerTestCase):
    CHECKER_CLASS = IdentifierNameChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(IdentifierNameChecker, 'report_error')
    def test_valid_names(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                int valid_variable = 0;
                void valid_function() {}
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(IdentifierNameChecker, 'report_error')
    def test_invalid_variable_name(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo() {
                    int invalidVariable = 0;
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == 'invalidVariable'),
            _Messages.get(self.checker.MSG, 'invalid_variable'),
        )

    @patch.object(IdentifierNameChecker, 'report_error')
    def test_invalid_function_name(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void InvalidFunction() {}
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == 'InvalidFunction'),
            _Messages.get(self.checker.MSG, 'invalid_function'),
        )


if __name__ == '__main__':
    main()
