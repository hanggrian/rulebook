from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.code_block_line_trimming_checker import CodeBlockLineTrimmingChecker

from .tests import msg


class TestCodeBlockLineTrimmingCheckerChecker(CheckerTestCase):
    CHECKER_CLASS = CodeBlockLineTrimmingChecker

    def test_class_block_without_first_newline(self):
        code = \
            '''
            class Foo:
                bar = 0
            '''
        with self.assertNoMessages():
            self.checker.process_tokens(_tokenize_str(code))

    def test_class_block_with_first_newline(self):
        code = \
            '''
            class Foo:

                bar = 0
            '''
        with self.assertAddsMessages(msg(CodeBlockLineTrimmingChecker.MSG, 3)):
            self.checker.process_tokens(_tokenize_str(code))

    def test_function_block_without_first_newline(self):
        code = \
            '''
            def foo():
                bar = 0
            '''
        with self.assertNoMessages():
            self.checker.process_tokens(_tokenize_str(code))

    def test_function_block_with_first_newline(self):
        code = \
            '''
            def foo(a: int):

                bar = 0
            '''
        with self.assertAddsMessages(msg(CodeBlockLineTrimmingChecker.MSG, 3)):
            self.checker.process_tokens(_tokenize_str(code))


if __name__ == '__main__':
    main()
