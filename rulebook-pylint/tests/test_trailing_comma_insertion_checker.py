from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.trailing_comma_insertion_checker import TrailingCommaInsertionChecker

from .tests import msg


class TestTrailingCommaInsertionChecker(CheckerTestCase):
    CHECKER_CLASS = TrailingCommaInsertionChecker

    def test_single_line_parameter(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(bar: int, baz: int):
                    print(1, 2)
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_multiline_parameter_with_trailing_comma(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(
                    bar: int,
                    baz: int,
                ):
                    print(
                        1,
                        2,
                    )
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_multiline_parameter_without_trailing_comma(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(
                    bar: int,
                    baz: int
                ):
                    print(
                        1,
                        2
                    )
                ''',
            )
        with self.assertAddsMessages(
            msg(TrailingCommaInsertionChecker.MSG, (4, 28)),
            msg(TrailingCommaInsertionChecker.MSG, (8, 25)),
        ):
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
