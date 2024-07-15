from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.code_block_line_trimming_checker import CodeBlockLineTrimmingChecker

from .tests import assert_properties, msg


class TestCodeBlockLineTrimmingCheckerChecker(CheckerTestCase):
    CHECKER_CLASS = CodeBlockLineTrimmingChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_class_block_without_first_newline(self):
        tokens = \
            _tokenize_str(
                '''
                class Foo:
                    bar = 0
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_class_block_with_first_newline(self):
        tokens = \
            _tokenize_str(
                '''
                class Foo:

                    bar = 0
                ''',
            )
        with self.assertAddsMessages(msg(CodeBlockLineTrimmingChecker.MSG, (3, 0))):
            self.checker.process_tokens(tokens)

    def test_function_block_without_first_newline(self):
        tokens = \
            _tokenize_str(
                '''
                def foo():
                    bar = 0
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_function_block_with_first_newline(self):
        tokens = \
            _tokenize_str(
                '''
                def foo(a: int):

                    bar = 0
                ''',
            )
        with self.assertAddsMessages(msg(CodeBlockLineTrimmingChecker.MSG, (3, 0))):
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
