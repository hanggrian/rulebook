from unittest import main

# noinspection protected-member
from pylint.testutils import CheckerTestCase, _tokenize_str

from rulebook_pylint.checkers import NoDuplicateBlankLineInCommentChecker
from testing.messages import msg
from ..asserts import assert_properties


# noinspection PyTypeChecker
class TestNoDuplicateBlankLineInCommentChecker(CheckerTestCase):
    CHECKER_CLASS = NoDuplicateBlankLineInCommentChecker

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
            msg(NoDuplicateBlankLineInCommentChecker._MSG, (4, 16)),
        ):
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
