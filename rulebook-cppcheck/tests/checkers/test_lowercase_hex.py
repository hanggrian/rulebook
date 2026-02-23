from textwrap import dedent
from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers.lowercase_hex import LowercaseHexChecker
from ..tests import CheckerTestCase, assert_properties


class TestLowercaseHexChecker(CheckerTestCase):
    CHECKER_CLASS = LowercaseHexChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(LowercaseHexChecker, 'report_error')
    def test_lowercase_hexadecimal_letters(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    int i = 0x00bb00;

                    void foo() {
                        cout << 0xaa00cc;
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(LowercaseHexChecker, 'report_error')
    def test_uppercase_hexadecimal_letters(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    int i = 0X00BB00;

                    void foo() {
                        cout << 0XAA00CC;
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '0X00BB00'),
                    "Use hexadecimal '0x00bb00'.",
                ),
                call(
                    next(t for t in tokens if t.str == '0XAA00CC'),
                    "Use hexadecimal '0xaa00cc'.",
                ),
            ],
        )


if __name__ == '__main__':
    main()
