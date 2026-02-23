from textwrap import dedent
from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers.lowercase_f import LowercaseFChecker
from ..tests import CheckerTestCase, assert_properties


class TestLowercaseFChecker(CheckerTestCase):
    CHECKER_CLASS = LowercaseFChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(LowercaseFChecker, 'report_error')
    def test_lowercase_literal_floats(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    float f = 0.0f;

                    void foo() {
                        cout << 123f;
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(LowercaseFChecker, 'report_error')
    def test_uppercase_literal_floats(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    float f = 0.0F;

                    void foo() {
                        cout << 123F;
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '0.0F'),
                    "Tag float literal by 'f'.",
                ),
                call(
                    next(t for t in tokens if t.str == '123F'),
                    "Tag float literal by 'f'.",
                ),
            ],
        )


if __name__ == '__main__':
    main()
