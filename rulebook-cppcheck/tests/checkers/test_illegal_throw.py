from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.illegal_throw import IllegalThrowChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestIllegalThrowChecker(CheckerTestCase):
    CHECKER_CLASS = IllegalThrowChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(IllegalThrowChecker, 'report_error')
    def test_throw_narrow_exceptions(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo() {
                    throw std::invalid_argument("foo");
                }
                void bar() {
                    throw std::runtime_error("bar");
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(IllegalThrowChecker, 'report_error')
    def test_throw_broad_exceptions(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo() {
                    throw std::exception();
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == 'exception'),
            _Messages.get(self.checker.MSG),
        )

    @patch.object(IllegalThrowChecker, 'report_error')
    def test_skip_throwing_by_reference(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo() {
                    auto e = std::exception();
                    throw e;
                }
                ''',
            )
        ]
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
