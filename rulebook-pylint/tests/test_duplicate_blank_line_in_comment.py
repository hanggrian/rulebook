from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.duplicate_blank_line_in_comment import DuplicateBlankLineInCommentChecker

from .tests import assert_properties, msg


class TestDuplicateBlankLineInCommentChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateBlankLineInCommentChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

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
            msg(DuplicateBlankLineInCommentChecker.MSG, (4, 16)),
        ):
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
