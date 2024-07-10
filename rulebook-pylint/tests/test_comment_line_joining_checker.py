from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.comment_line_joining_checker import CommentLineJoiningChecker

from .tests import msg


class TestCommentLineJoiningChecker(CheckerTestCase):
    CHECKER_CLASS = CommentLineJoiningChecker

    def test_single_empty_line_in_comment(self):
        tokens = \
            _tokenize_str(
                '''
                # Lorem ipsum
                #
                # dolor sit amet.
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_multiple_empty_lines_in_comment(self):
        tokens = \
            _tokenize_str(
                '''
                # Lorem ipsum
                #
                #
                # dolor sit amet.
                ''',
            )
        with self.assertAddsMessages(
            msg(CommentLineJoiningChecker.MSG, (4, 16)),
        ):
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
