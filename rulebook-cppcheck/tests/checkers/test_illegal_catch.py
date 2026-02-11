from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.illegal_catch import IllegalCatchChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestIllegalCatchChecker(CheckerTestCase):
    CHECKER_CLASS = IllegalCatchChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(IllegalCatchChecker, 'report_error')
    def test_ellipsis_violation(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo() {
                    try {
                    } catch (...) {
                    }
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == 'catch'),
            _Messages.get(self.checker.MSG),
        )

    @patch.object(IllegalCatchChecker, 'report_error')
    def test_std_exception_violation(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo() {
                    try {
                    } catch (const std::exception& e) {
                    }
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == 'catch'),
            _Messages.get(self.checker.MSG),
        )

    @patch.object(IllegalCatchChecker, 'report_error')
    def test_specific_exception_valid(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo() {
                    try {
                    } catch (const std::runtime_error& e) {
                    }
                }
                ''',
            )
        ]
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
