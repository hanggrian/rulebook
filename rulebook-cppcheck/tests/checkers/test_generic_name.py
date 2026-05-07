from textwrap import dedent
from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers import GenericNameChecker
from .checker_case import CheckerTestCase
from ..asserts import assert_properties


class TestGenericNameChecker(CheckerTestCase):
    CHECKER_CLASS = GenericNameChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(GenericNameChecker, 'report_error')
    def test_correct_generic_name_in_class(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    template <typename GenericValue> class MyClass {}
                    template <class E> interface MyInterface {}
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(GenericNameChecker, 'report_error')
    def test_incorrect_generic_name_in_class(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    template <typename TYPE> class MyClass {}
                    template <typename generic_value> class MyClass2 {}
                    template <typename element> class MyClass3 {}
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'TYPE'),
                    'Use pascal-case name.',
                ),
                call(
                    next(t for t in tokens if t.str == 'generic_value'),
                    'Use pascal-case name.',
                ),
                call(
                    next(t for t in tokens if t.str == 'element'),
                    'Use pascal-case name.',
                ),
            ],
        )

    @patch.object(GenericNameChecker, 'report_error')
    def test_correct_generic_name_in_function(self, report_error):
        tokens, _ = self.dump_code('template <typename E> void execute() {}')
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(GenericNameChecker, 'report_error')
    def test_incorrect_generic_name_in_function(self, report_error):
        tokens, _ = \
            self.dump_code(
                dedent(
                    '''
                    template <typename generic_value> void execute() {}
                    template <typename element> void execute2() {}
                    ''',
                ),
            )
        self.checker.process_tokens(tokens)
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'generic_value'),
                    'Use pascal-case name.',
                ),
                call(
                    next(t for t in tokens if t.str == 'element'),
                    'Use pascal-case name.',
                ),
            ],
        )


if __name__ == '__main__':
    main()
