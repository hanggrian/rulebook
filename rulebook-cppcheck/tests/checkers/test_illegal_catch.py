from textwrap import dedent
from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.illegal_catch import IllegalCatchChecker
from ..tests import CheckerTestCase, assert_properties


class TestIllegalCatchChecker(CheckerTestCase):
    CHECKER_CLASS = IllegalCatchChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(IllegalCatchChecker, 'report_error')
    def test_ellipsis_violation(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        try {
                        } catch (...) {
                        }
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'catch'),
            'Catch a narrower type.',
        )

    @patch.object(IllegalCatchChecker, 'report_error')
    def test_std_exception_violation(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        try {
                        } catch (const std::exception& e) {
                        }
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'catch'),
            'Catch a narrower type.',
        )

    @patch.object(IllegalCatchChecker, 'report_error')
    def test_specific_exception_valid(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        try {
                        } catch (const std::runtime_error& e) {
                        }
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
