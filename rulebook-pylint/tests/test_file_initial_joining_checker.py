from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.file_initial_joining_checker import FileInitialJoiningChecker

from .tests import msg

class TestFileInitialJoiningChecker(CheckerTestCase):
    CHECKER_CLASS = FileInitialJoiningChecker

    def test_trimmed_file(self):
        code = \
            '''import unittest
            '''
        with self.assertNoMessages():
            self.checker.process_tokens(_tokenize_str(code))

    def test_padded_file(self):
        code = \
            '''
            import unittest
            '''
        with self.assertAddsMessages(msg(FileInitialJoiningChecker.MSG, 0)):
            self.checker.process_tokens(_tokenize_str(code))

if __name__ == '__main__':
    main()
