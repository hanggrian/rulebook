from textwrap import dedent
from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers import NoBroadThrowChecker
from .checker_case import CheckerTestCase
from ..asserts import assert_properties


# noinspection PyUnresolvedReferences
class TestNoBroadThrowChecker(CheckerTestCase):
    CHECKER_CLASS = NoBroadThrowChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(NoBroadThrowChecker, 'report_error')
    def test_throw_narrow_exceptions(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        throw std::invalid_argument("foo");
                    }
                    void bar() {
                        throw std::runtime_error("bar");
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(NoBroadThrowChecker, 'report_error')
    def test_throw_broad_exceptions(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        throw std::exception();
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'exception'),
            'Throw a narrower type.',
        )

    @patch.object(NoBroadThrowChecker, 'report_error')
    def test_skip_throwing_by_reference(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    void foo() {
                        auto e = std::exception();
                        throw e;
                    }
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
