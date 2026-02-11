from unittest import main
from unittest.mock import patch, call

from rulebook_cppcheck.checkers.redundant_else import RedundantElseChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestRedundantElseChecker(CheckerTestCase):
    CHECKER_CLASS = RedundantElseChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(RedundantElseChecker, 'report_error')
    def test_no_throw_or_return_in_if(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
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
        ]
        mock_report.assert_not_called()

    @patch.object(RedundantElseChecker, 'report_error')
    def test_lift_else_when_if_has_return(self, mock_report):
        tokens = \
            self.dump_tokens(
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
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'else' and t.linenr == 5),
                    _Messages.get(self.checker.MSG),
                ),
                call(
                    next(t for t in tokens if t.str == 'else' and t.linenr == 7),
                    _Messages.get(self.checker.MSG),
                ),
            ],
            any_order=True,
        )

    @patch.object(RedundantElseChecker, 'report_error')
    def test_consider_if_else_without_blocks(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo() {
                    if (true) throw std::exception();
                    else if (false) return;
                    else baz();
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'else' and t.linenr == 4),
                    _Messages.get(self.checker.MSG),
                ),
                call(
                    next(t for t in tokens if t.str == 'else' and t.linenr == 5),
                    _Messages.get(self.checker.MSG),
                ),
            ],
            any_order=True,
        )


if __name__ == '__main__':
    main()
