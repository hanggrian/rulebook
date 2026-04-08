from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str

from rulebook_pylint.checkers import DuplicateSpaceChecker
from testing.messages import msg
from ..asserts import assert_properties


# noinspection PyTypeChecker
class TestDuplicateSpaceChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateSpaceChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_single_space_between_token(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(bar, baz):
                    qux = 1 + 2
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_multiple_spaces_between_token(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(bar,  baz):
                    qux = 1 +\t 2
                ''',
            )
        with self.assertAddsMessages(
            msg(DuplicateSpaceChecker._MSG, (2, 27)),
            msg(DuplicateSpaceChecker._MSG, (3, 28)),
        ):
            self.checker.process_tokens(tokens)

    def test_skip_block_comment_asterisk(self):
        tokens = \
            _tokenize_str(
                """
                def foo():
                    bar = '  ';
                    baz = f'  ';
                """,
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_skip_block_comment_docstring(self):
        tokens = \
            _tokenize_str(
                """
                def foo():
                    '''
                    return the new size
                        of the group.
                    '''
                    pass
                """,
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_skip_duplicate_space_before_comment(self):
        tokens = \
            _tokenize_str(
                """
                def foo():
                    bar = 0  # baz
                """,
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
