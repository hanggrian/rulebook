from unittest import main
from unittest.mock import patch, call

from rulebook_cppcheck.checkers.generic_name import GenericNameChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestGenericNameChecker(CheckerTestCase):
    CHECKER_CLASS = GenericNameChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(GenericNameChecker, 'report_error')
    def test_correct_generic_name_in_class(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                template <typename T> class MyClass {}
                template <class V> interface MyInterface {}
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(GenericNameChecker, 'report_error')
    def test_incorrect_generic_name_in_class(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                template <typename XA> class MyClass {}
                template <typename Xa> class MyClass2 {}
                template <typename aX> class MyClass3 {}
                template <typename a_x> class MyClass4 {}
                template <typename A_X> class MyClass5 {}
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'XA'),
                    _Messages.get(self.checker.MSG),
                ),
                call(
                    next(t for t in tokens if t.str == 'Xa'),
                    _Messages.get(self.checker.MSG),
                ),
                call(
                    next(t for t in tokens if t.str == 'aX'),
                    _Messages.get(self.checker.MSG),
                ),
                call(
                    next(t for t in tokens if t.str == 'a_x'),
                    _Messages.get(self.checker.MSG),
                ),
                call(
                    next(t for t in tokens if t.str == 'A_X'),
                    _Messages.get(self.checker.MSG),
                ),
            ],
            any_order=True,
        )

    @patch.object(GenericNameChecker, 'report_error')
    def test_correct_generic_name_in_function(self, mock_report):
        [
            self.checker.process_token(token)
            for token in self.dump_tokens('template <typename E> void execute() {}')
        ]
        mock_report.assert_not_called()

    @patch.object(GenericNameChecker, 'report_error')
    def test_incorrect_generic_name_in_function(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                template <typename Xa> void execute() {}
                template <typename aX> void execute2() {}
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'Xa'),
                    _Messages.get(self.checker.MSG),
                ),
                call(
                    next(t for t in tokens if t.str == 'aX'),
                    _Messages.get(self.checker.MSG),
                ),
            ],
            any_order=True,
        )

    @patch.object(GenericNameChecker, 'report_error')
    def test_skip_multiple_generics(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                template <typename Xa, typename Ax> class Foo {}
                template <typename Bar, typename Baz> void bar() {}
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(GenericNameChecker, 'report_error')
    def test_skip_inner_generics(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                template <typename T> class Foo {
                    template <typename X> class Bar {}
                    template <typename Y> void bar() {}
                };
                ''',
            )
        ]
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
