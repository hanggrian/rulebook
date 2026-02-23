from textwrap import dedent
from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers.chain_call_wrap import ChainCallWrapChecker
from ..tests import CheckerTestCase, assert_properties


class TestChainCallWrapChecker(CheckerTestCase):
    CHECKER_CLASS = ChainCallWrapChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_aligned_chain_method_call_continuation(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        StringBuilder("Lorem ipsum")
                            .append(0)
                            .append(1);
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_misaligned_chain_method_call_continuation(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        StringBuilder(
                            "Lorem ipsum"
                        )
                            .append(0)
                            .append(1);
                    }
                    ''',
                ),
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
                dedent(
                    '''
                    void foo() {
                        StringBuilder("Lorem ipsum")
                            .append(0).append(1)
                            .append(2);
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '.' and t.column == 19),
                    "Put newline before '.'.",
                ),
            ],
        )

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_also_capture_non_method_call(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        baz()
                            .baz().qux
                            .baz();
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '.' and t.column == 15),
                    "Put newline before '.'.",
                ),
            ],
        )

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_allow_dots_on_single_line(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
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
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_skip_single_line_parameters(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    const bool haveMisraAddon = std::any_of(settings.addonInfos.cbegin(),
                                                            settings.addonInfos.cend(),
                                                            [] (const AddonInfo &info) {
                        return info.name == "misra";
                    });
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(ChainCallWrapChecker, 'report_error')
    def test_skip_single_line_in_ternary(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    int foo() {
                        return m_long_names.empty()
                            ? m_short_name
                            : string(m_short_name).append(" [ --");
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
