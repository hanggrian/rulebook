from unittest import main

# noinspection protected-member
from pylint.testutils import CheckerTestCase, _tokenize_str

from rulebook_pylint.checkers import CommentTrimmingChecker
from testing.messages import msg
from ..asserts import assert_properties


# noinspection PyTypeChecker
class TestCommentTrimmingChecker(CheckerTestCase):
    CHECKER_CLASS = CommentTrimmingChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_comment_without_initial_and_final_newline(self):
        tokens = \
            _tokenize_str(
                '''
                # Lorem ipsum.
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_comment_with_initial_and_final_newline(self):
        tokens = \
            _tokenize_str(
                '''
                #
                # Lorem ipsum.
                #
                ''',
            )
        with self.assertAddsMessages(
            msg(CommentTrimmingChecker._MSG, (2, 16)),
            msg(CommentTrimmingChecker._MSG, (4, 16)),
        ):
            self.checker.process_tokens(tokens)

    def test_skip_blank_comment(self):
        tokens = \
            _tokenize_str(
                '''

                #

                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
