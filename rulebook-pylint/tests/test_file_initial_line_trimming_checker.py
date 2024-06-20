from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.file_initial_line_trimming_checker import FileInitialLineTrimmingChecker

from .tests import msg


class TestFileInitialLineTrimmingChecker(CheckerTestCase):
    CHECKER_CLASS = FileInitialLineTrimmingChecker

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
        with self.assertAddsMessages(msg(FileInitialLineTrimmingChecker.MSG, 0)):
            self.checker.process_tokens(_tokenize_str(code))


if __name__ == '__main__':
    main()
