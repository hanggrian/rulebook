from textwrap import dedent
from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.redundant_default import RedundantDefaultChecker
from ..tests import CheckerTestCase, assert_properties


class TestRedundantDefaultChecker(CheckerTestCase):
    CHECKER_CLASS = RedundantDefaultChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(RedundantDefaultChecker, 'report_error')
    def test_no_throw_or_return_in_case(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo(int bar) {
                        switch (bar) {
                            case 0:
                                baz();
                                break;
                            case 1:
                                baz();
                                break;
                            default:
                                baz();
                                break;
                        }
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(RedundantDefaultChecker, 'report_error')
    def test_lift_else_when_case_has_return(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo(int bar) {
                        switch (bar) {
                            case 0:
                                throw std::exception();
                            case 1:
                                return;
                            default:
                                baz();
                                break;
                        }
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == ':' and t.linenr == 8),
            "Omit redundant 'default' condition.",
        )

    @patch.object(RedundantDefaultChecker, 'report_error')
    def test_skip_if_not_all_case_blocks_have_jump_statement(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo(int bar) {
                        switch (bar) {
                            case 0:
                                return;
                            case 1:
                                baz();
                                break;
                            default:
                                baz();
                                break;
                        }
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
