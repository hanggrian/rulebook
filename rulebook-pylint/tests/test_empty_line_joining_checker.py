from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.empty_line_joining_checker import EmptyLineJoiningChecker

from .tests import msg

class TestEmptyLineJoiningChecker(CheckerTestCase):
    CHECKER_CLASS = EmptyLineJoiningChecker

    def test_single_empty_line(self):
        code = \
            '''
            foo = 0

            bar = 1
            '''
        with self.assertNoMessages():
            self.checker.process_tokens(_tokenize_str(code))

    def test_double_empty_line(self):
        code = \
            '''
            foo = 0


            bar = 1
            '''
        with self.assertAddsMessages(msg(EmptyLineJoiningChecker.MSG, 4)):
            self.checker.process_tokens(_tokenize_str(code))

if __name__ == '__main__':
    main()
