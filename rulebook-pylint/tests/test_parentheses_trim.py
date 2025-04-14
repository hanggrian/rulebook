from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.parentheses_trim import ParenthesesTrimChecker

from .tests import assert_properties, msg


class TestParenthesesTrimChecker(CheckerTestCase):
    CHECKER_CLASS = ParenthesesTrimChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_parentheses_without_newline_padding(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(
                    bar: int,
                ):
                    baz = {
                        1,
                    }
                    qux = [
                        3,
                    ]
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_parentheses_with_newline_padding(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(

                    bar: int,

                ):
                    baz = {

                        1,

                    }
                    qux = [

                        3,

                    ]
                ''',
            )
        with self.assertAddsMessages(
            msg(ParenthesesTrimChecker.MSG_FIRST, (3, 0), args='('),
            msg(ParenthesesTrimChecker.MSG_LAST, (5, 0), args=')'),
            msg(ParenthesesTrimChecker.MSG_FIRST, (8, 0), args='{'),
            msg(ParenthesesTrimChecker.MSG_LAST, (10, 0), args='}'),
            msg(ParenthesesTrimChecker.MSG_FIRST, (13, 0), args='['),
            msg(ParenthesesTrimChecker.MSG_LAST, (15, 0), args=']'),
        ):
            self.checker.process_tokens(tokens)

    def test_comments_are_considered_content(self):
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
