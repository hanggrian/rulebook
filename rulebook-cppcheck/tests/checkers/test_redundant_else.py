from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers.redundant_else import RedundantElseChecker
from ..tests import CheckerTestCase, assert_properties


class TestRedundantElseChecker(CheckerTestCase):
    CHECKER_CLASS = RedundantElseChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(RedundantElseChecker, 'report_error')
    def test_no_throw_or_return_in_if(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    if (true) {
                        baz();
                    } else if (false) {
                        baz();
                    } else {
                        baz();
                    }
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(RedundantElseChecker, 'report_error')
    def test_lift_else_when_if_has_return(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    if (true) {
                        throw std::exception();
                    } else if (false) {
                        return;
                    } else {
                        baz();
                    }
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'else' and t.linenr == 5),
                    "Omit redundant 'else' condition.",
                ),
                call(
                    next(t for t in tokens if t.str == 'else' and t.linenr == 7),
                    "Omit redundant 'else' condition.",
                ),
            ],
        )

    @patch.object(RedundantElseChecker, 'report_error')
    def test_consider_if_else_without_blocks(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    if (true) throw std::exception();
                    else if (false) return;
                    else baz();
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'else' and t.linenr == 4),
                    "Omit redundant 'else' condition.",
                ),
                call(
                    next(t for t in tokens if t.str == 'else' and t.linenr == 5),
                    "Omit redundant 'else' condition.",
                ),
            ],
        )


if __name__ == '__main__':
    main()
