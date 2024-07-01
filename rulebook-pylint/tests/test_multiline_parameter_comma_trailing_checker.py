from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.multiline_parameter_comma_trailing_checker \
    import MultilineParameterCommaTrailingChecker

from .tests import msg


class TestMultilineParameterCommaTrailingChecker(CheckerTestCase):
    CHECKER_CLASS = MultilineParameterCommaTrailingChecker

    def test_single_line_parameter(self):
        code = \
            '''
            def foo(bar: int, baz: int):
                print(1, 2)
            '''
        with self.assertNoMessages():
            self.checker.process_tokens(_tokenize_str(code))

    def test_multiline_parameter_with_trailing_comma(self):
        code = \
            '''
            def foo(
                bar: int,
                baz: int,
            ):
                print(
                    1,
                    2,
                )
            '''
        with self.assertNoMessages():
            self.checker.process_tokens(_tokenize_str(code))

    def test_multiline_parameter_without_trailing_comma(self):
        code = \
            '''
            def foo(
                bar: int,
                baz: int
            ):
                print(
                    1,
                    2
                )
            '''
        with self.assertAddsMessages(
            msg(MultilineParameterCommaTrailingChecker.MSG, (4, 24)),
            msg(MultilineParameterCommaTrailingChecker.MSG, (8, 21)),
        ):
            self.checker.process_tokens(_tokenize_str(code))


if __name__ == '__main__':
    main()
