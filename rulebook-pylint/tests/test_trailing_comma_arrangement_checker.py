from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.trailing_comma_arrangement_checker import TrailingCommaArrangementChecker

from .tests import assert_properties, msg


class TestTrailingCommaArrangementChecker(CheckerTestCase):
    CHECKER_CLASS = TrailingCommaArrangementChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_single_line_parameter_without_trailing_comma(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(bar: int, baz: int):
                    print(1, 2)
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_single_line_parameter_with_trailing_comma(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(bar: int, baz: int,):
                    print(1, 2,)
                ''',
            )
        with self.assertAddsMessages(
            msg(TrailingCommaArrangementChecker.MSG_SINGLE, (2, 43)),
            msg(TrailingCommaArrangementChecker.MSG_SINGLE, (3, 31)),
        ):
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
            msg(TrailingCommaArrangementChecker.MSG_MULTI, (4, 28)),
            msg(TrailingCommaArrangementChecker.MSG_MULTI, (8, 25)),
        ):
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

    def test_skip_inline_comment(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(
                    bar: int,  # bar
                    baz: int,  # baz
                ):
                    print(
                        1,  # 1
                        2,  # 2
                    )
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
