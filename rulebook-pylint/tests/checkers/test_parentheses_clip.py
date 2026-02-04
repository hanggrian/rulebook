from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str

from rulebook_pylint.checkers.parentheses_clip import ParenthesesClipChecker
from ..tests import assert_properties, msg


# noinspection PyTypeChecker
class TestParenthesesClipChecker(CheckerTestCase):
    CHECKER_CLASS = ParenthesesClipChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_wrapped_parentheses(self):
        tokens = \
            _tokenize_str(
                '''
                def foo():
                    baz = {}
                    qux = []
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_unwrapped_parentheses(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(
                ):
                    baz = {
                    }
                    qux = [
                    ]

                def foo2( ):
                    baz2 = { }
                    qux2 = [ ]
                ''',
            )
        with self.assertAddsMessages(
            msg(ParenthesesClipChecker.MSG, (2, 23, 3, 17), args='()'),
            msg(ParenthesesClipChecker.MSG, (4, 26, 5, 21), args='{}'),
            msg(ParenthesesClipChecker.MSG, (6, 26, 7, 21), args='[]'),
            msg(ParenthesesClipChecker.MSG, (9, 24, 9, 27), args='()'),
            msg(ParenthesesClipChecker.MSG, (10, 27, 10, 30), args='{}'),
            msg(ParenthesesClipChecker.MSG, (11, 27, 11, 30), args='[]'),
        ):
            self.checker.process_tokens(tokens)

    def test_allow_parentheses_with_comment(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(
                    # Lorem ipsum.
                    bar: int,
                    # Lorem ipsum.
                ):
                    baz = {
                        # Lorem ipsum.
                        1,
                        # Lorem ipsum.
                    }
                    qux = [
                        # Lorem ipsum.
                        3,
                        # Lorem ipsum.
                    ]
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
