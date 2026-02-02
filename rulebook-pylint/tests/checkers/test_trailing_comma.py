from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str

from rulebook_pylint.checkers.trailing_comma import TrailingCommaChecker
from ..tests import assert_properties, msg


# noinspection PyTypeChecker
class TestTrailingCommaChecker(CheckerTestCase):
    CHECKER_CLASS = TrailingCommaChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_single_line_parameter_without_trailing_comma(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(bar: int, baz: int):
                    print(1, 2)
                    qux = [3, 4]
                    qux2 = {5, 6}
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
                    qux = [3, 4,]
                    qux2 = {5, 6,}
                ''',
            )
        with self.assertAddsMessages(
            msg(TrailingCommaChecker.MSG_SINGLE, (2, 43)),
            msg(TrailingCommaChecker.MSG_SINGLE, (3, 31)),
            msg(TrailingCommaChecker.MSG_SINGLE, (4, 32)),
            msg(TrailingCommaChecker.MSG_SINGLE, (5, 33)),
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
                    qux = [
                        3,
                        4
                    ]
                    qux2 = {
                        5,
                        6
                    }
                ''',
            )
        with self.assertAddsMessages(
            msg(TrailingCommaChecker.MSG_MULTI, (4, 28)),
            msg(TrailingCommaChecker.MSG_MULTI, (8, 25)),
            msg(TrailingCommaChecker.MSG_MULTI, (12, 25)),
            msg(TrailingCommaChecker.MSG_MULTI, (16, 25)),
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
                    qux = [
                        3,
                        4,
                    ]
                    qux2 = {
                        5,
                        6,
                    }
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
                    qux = [
                        3,  # 3
                        4,  # 4
                    ]
                    qux2 = {
                        5,  # 5
                        6,  # 6
                    }
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
