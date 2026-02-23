from textwrap import dedent
from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers.illegal_variable_name import IllegalVariableNameChecker
from ..tests import CheckerTestCase, assert_properties


class TestIllegalVariableNameChecker(CheckerTestCase):
    CHECKER_CLASS = IllegalVariableNameChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(IllegalVariableNameChecker, 'report_error')
    def test_descriptive_names(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    int age = 0;
                    string[1] names = {""};
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(IllegalVariableNameChecker, 'report_error')
    def test_prohibited_names(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    int integer = 0;
                    string strings[1] = {""};
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'integer'),
                    'Use descriptive name.',
                ),
                call(
                    next(t for t in tokens if t.str == 'strings'),
                    'Use descriptive name.',
                ),
            ],
        )


if __name__ == '__main__':
    main()
