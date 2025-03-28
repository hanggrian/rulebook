from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.code_block_trim import CodeBlockTrim

from .tests import assert_properties, msg


class TestCodeBlockTrim(CheckerTestCase):
    CHECKER_CLASS = CodeBlockTrim

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_code_blocks_without_newline_padding(self):
        tokens = \
            _tokenize_str(
                '''
                class Foo:
                    def foo():
                        baz = 0
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_code_blocks_with_newline_padding(self):
        tokens = \
            _tokenize_str(
                '''
                class Foo:

                    def bar():

                        baz = 0
                ''',
            )
        with self.assertAddsMessages(
            msg(CodeBlockTrim.MSG, (3, 0)),
            msg(CodeBlockTrim.MSG, (5, 0)),
        ):
            self.checker.process_tokens(tokens)

    def test_block_comment_and_annotations_in_members(self):
        tokens = \
            _tokenize_str(
                '''
                class Foo:
                    """Lorem ipsum."""
                    def foo():
                        """Lorem ipsum."""
                        baz = 0
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_comment_in_members(self):
        tokens = \
            _tokenize_str(
                '''
                class Foo:
                    # Lorem ipsum.
                    def foo():
                        # Lorem ipsum.
                        baz = 0
                        # Lorem ipsum.
                    # Lorem ipsum.
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
