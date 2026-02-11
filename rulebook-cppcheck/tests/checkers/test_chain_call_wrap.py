from unittest import main
from unittest.mock import patch, call

from rulebook_cppcheck.checkers.chain_call_wrap import ChainCallWrapChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestChainCallWrapChecker(CheckerTestCase):
    CHECKER_CLASS = ChainCallWrapChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_aligned_chain_method_call_continuation(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo() {
                    StringBuilder("Lorem ipsum")
                        .append(0)
                        .append(1);
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_misaligned_chain_method_call_continuation(self, mock_report):
        tokens = \
            self.dump_tokens(
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
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == '.' and t.linenr == 6),
            _Messages.get(self.checker.MSG_UNEXPECTED),
        )

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_inconsistent_dot_position(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo() {
                    StringBuilder("Lorem ipsum")
                        .append(0).append(1)
                        .append(2);
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '.' and t.column == 35),
                    _Messages.get(self.checker.MSG_MISSING),
                ),
            ],
            any_order=True,
        )

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_also_capture_non_method_call(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo() {
                    baz()
                        .baz().qux
                        .baz();
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '.' and t.column == 31),
                    _Messages.get(self.checker.MSG_MISSING),
                ),
            ],
            any_order=True,
        )

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_allow_dots_on_single_line(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
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
        ]
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
