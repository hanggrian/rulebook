from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.file_initial_line_trimming_checker import FileInitialLineTrimmingChecker

from .tests import assert_properties, msg


class TestFileInitialLineTrimmingChecker(CheckerTestCase):
    CHECKER_CLASS = FileInitialLineTrimmingChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_trimmed_file(self):
        tokens = \
            _tokenize_str(
                '''import unittest
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_padded_file(self):
        tokens = \
            _tokenize_str(
                '''
                import unittest
                ''',
            )
        with self.assertAddsMessages(msg(FileInitialLineTrimmingChecker.MSG, 0)):
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
