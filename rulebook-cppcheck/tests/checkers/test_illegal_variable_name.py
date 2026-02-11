from unittest import main
from unittest.mock import patch, call

from rulebook_cppcheck.checkers.illegal_variable_name import IllegalVariableNameChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestIllegalVariableNameChecker(CheckerTestCase):
    CHECKER_CLASS = IllegalVariableNameChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(IllegalVariableNameChecker, 'report_error')
    def test_descriptive_names(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                int age = 0;
                string[1] names = {""};
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(IllegalVariableNameChecker, 'report_error')
    def test_prohibited_names(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                int integer = 0;
                string strings[1] = {""};
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'integer'),
                    _Messages.get(self.checker.MSG),
                ),
                call(
                    next(t for t in tokens if t.str == 'strings'),
                    _Messages.get(self.checker.MSG),
                ),
            ],
            any_order=True,
        )


if __name__ == '__main__':
    main()
