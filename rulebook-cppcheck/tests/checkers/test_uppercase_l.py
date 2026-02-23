from textwrap import dedent
from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers.uppercase_l import UppercaseLChecker
from ..tests import CheckerTestCase, assert_properties


class TestUppercaseLChecker(CheckerTestCase):
    CHECKER_CLASS = UppercaseLChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(UppercaseLChecker, 'report_error')
    def test_uppercase_literal_longs(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    long l = 0L;

                    void foo() {
                        cout << 100L;
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(UppercaseLChecker, 'report_error')
    def test_lowercase_literal_longs(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    long l = 0l;

                    void foo() {
                        cout << 100l;
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '0l'),
                    "Tag long literal by 'L'.",
                ),
                call(
                    next(t for t in tokens if t.str == '100l'),
                    "Tag long literal by 'L'.",
                ),
            ],
        )


if __name__ == '__main__':
    main()
