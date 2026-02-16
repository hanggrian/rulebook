from unittest import main
from unittest.mock import patch, call

from rulebook_cppcheck.checkers.chain_call_wrap import ChainCallWrapChecker
from ..tests import assert_properties, CheckerTestCase


class TestChainCallWrapChecker(CheckerTestCase):
    CHECKER_CLASS = ChainCallWrapChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_aligned_chain_method_call_continuation(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    StringBuilder("Lorem ipsum")
                        .append(0)
                        .append(1);
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_misaligned_chain_method_call_continuation(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    StringBuilder(
                        "Lorem ipsum"
                    )
                        .append(0)
                        .append(1);
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == '.' and t.linenr == 6),
            "Omit newline before '.'.",
        )

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_inconsistent_dot_position(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    StringBuilder("Lorem ipsum")
                        .append(0).append(1)
                        .append(2);
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '.' and t.column == 35),
                    "Put newline before '.'.",
                ),
            ],
            any_order=True,
        )

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_also_capture_non_method_call(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    baz()
                        .baz().qux
                        .baz();
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '.' and t.column == 31),
                    "Put newline before '.'.",
                ),
            ],
            any_order=True,
        )

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_allow_dots_on_single_line(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    StringBuilder(
                        0
                    ).append(1).append(2);
                    StringBuilder(0).append(1).append(
                        2
                    );
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
