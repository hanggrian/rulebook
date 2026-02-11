from unittest import main
from unittest.mock import patch, call

from rulebook_cppcheck.checkers.duplicate_space import DuplicateSpaceChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestDuplicateSpaceChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateSpaceChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_single_space_between_token(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo(int bar, int baz) {
                    int qux = 1 + 2;
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_multiple_spaces_between_token(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo(int bar, int baz) {
                    int qux =  1  +  2;
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == '1'),
                    _Messages.get(self.checker.MSG),
                ),
                call(
                    next(t for t in tokens if t.str == '+'),
                    _Messages.get(self.checker.MSG),
                ),
            ],
            any_order=True,
        )


if __name__ == '__main__':
    main()
