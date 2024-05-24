from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase
from rulebook_pylint.file_initial_wrapping_checker import FileInitialWrappingChecker

from .tests import msg

class TestFileInitialWrappingChecker(CheckerTestCase):
    CHECKER_CLASS = FileInitialWrappingChecker

    def test_trimmed_file(self):
        def_all = \
            parse(
                '''import unittest #@
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(def_all)

    def test_padded_file(self):
        def_all = \
            parse(
                '''
                import unittest #@
                ''',
            )
        with self.assertAddsMessages(msg(FileInitialWrappingChecker.MSG, 0)):
            self.checker.process_module(def_all)

if __name__ == '__main__':
    main()
