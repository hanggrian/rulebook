from unittest import main

# noinspection protected-member
from pylint.testutils import CheckerTestCase, _tokenize_str
from testing.messages import msg

from rulebook_pylint.checkers import CommentSpacingChecker
from ..asserts import assert_properties


# noinspection PyTypeChecker
class TestCommentSpacingChecker(CheckerTestCase):
    CHECKER_CLASS = CommentSpacingChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_with_whitespace(self):
        tokens = \
            _tokenize_str(
                '''
                # good
                # text
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_without_whitespace(self):
        tokens = \
            _tokenize_str(
                '''
                #bad
                #text
                ''',
            )
        with self.assertAddsMessages(
            msg(CommentSpacingChecker._MSG, (2, 16)),
            msg(CommentSpacingChecker._MSG, (3, 16)),
        ):
            self.checker.process_tokens(tokens)

    def test_ignore_block_comment(self):
        tokens = \
            _tokenize_str(
                '''
                """ignore
                block"""
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_capture_repeated_slashes_without_content(self):
        tokens = \
            _tokenize_str(
                '''
                ########
                # good #
                ########

                #### #######
                # not good #
                ######## ###
                ''',
            )
        with self.assertAddsMessages(
            msg(CommentSpacingChecker._MSG, (6, 16)),
            msg(CommentSpacingChecker._MSG, (8, 16)),
        ):
            self.checker.process_tokens(tokens)

    def test_skip_comment_in_comments(self):
        tokens = \
            _tokenize_str(
                '''
                // https://www.website.com
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_ignore_env_string(self):
        tokens = \
            _tokenize_str(
                '''
                #!/usr/bin/env python3
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
