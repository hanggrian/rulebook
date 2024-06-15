from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.empty_comment_line_joining_checker import EmptyCommentLineJoiningChecker

from .tests import msg


class TestEmptyCommentLineJoiningChecker(CheckerTestCase):
    CHECKER_CLASS = EmptyCommentLineJoiningChecker

    def test_single_empty_line_in_comment(self):
        code = \
            '''
            # Lorem ipsum
            #
            # dolor sit amet.
            '''
        with self.assertNoMessages():
            self.checker.process_tokens(_tokenize_str(code))

    def test_multiple_empty_lines_in_comment(self):
        code = \
            '''
            # Lorem ipsum
            #
            #
            # dolor sit amet.
            '''
        with self.assertAddsMessages(
            msg(EmptyCommentLineJoiningChecker.MSG, 4),
        ):
            self.checker.process_tokens(_tokenize_str(code))


if __name__ == '__main__':
    main()
