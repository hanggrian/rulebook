from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers import ComplicatedAssignmentChecker
from .checker_case import CheckerTestCase
from ..asserts import assert_properties


class TestComplicatedAssignmentChecker(CheckerTestCase):
    CHECKER_CLASS = ComplicatedAssignmentChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ComplicatedAssignmentChecker, 'report_error')
    def test_shorthand_assignments(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    int bar = 0;
                    bar += 1;
                    bar -= 1;
                    bar *= 1;
                    bar /= 1;
                    bar %= 1;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(ComplicatedAssignmentChecker, 'report_error')
    def test_complicated_assignments(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    int bar = 0;
                    bar = bar + 1;
                    bar = bar - 1;
                    bar = bar * 1;
                    bar = bar / 1;
                    bar = bar % 1;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '=' and t.linenr == 4),
                    "Use assignment operator '+='.",
                ),
                call(
                    next(t for t in tokens if t.str == '=' and t.linenr == 5),
                    "Use assignment operator '-='.",
                ),
                call(
                    next(t for t in tokens if t.str == '=' and t.linenr == 6),
                    "Use assignment operator '*='.",
                ),
                call(
                    next(t for t in tokens if t.str == '=' and t.linenr == 7),
                    "Use assignment operator '/='.",
                ),
                call(
                    next(t for t in tokens if t.str == '=' and t.linenr == 8),
                    "Use assignment operator '%='.",
                ),
            ],
        )

    @patch.object(ComplicatedAssignmentChecker, 'report_error')
    def test_target_leftmost_operator(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    int bar = 0;
                    bar = bar + 1 - 2 * 3 / 4 % 5;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == '=' and t.linenr == 4),
            "Use assignment operator '+='.",
        )


if __name__ == '__main__':
    main()
