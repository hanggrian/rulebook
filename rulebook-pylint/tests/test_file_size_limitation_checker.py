from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase
from rulebook_pylint.file_size_limitation_checker import FileSizeLimitationChecker

from .tests import msg

class TestFileSizeLimitationChecker(CheckerTestCase):
    CHECKER_CLASS = FileSizeLimitationChecker
    CHECKER_CLASS.options[0][1]['default'] = 3

    def test_small_size(self):
        node_all = \
            parse(
                '''
                print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(node_all)

    def test_large_file(self):
        node_all = \
            parse(
                '''
                print()
                print()
                ''',
            )
        with self.assertAddsMessages(msg(FileSizeLimitationChecker.MSG, 0, args=3)):
            self.checker.process_module(node_all)

if __name__ == '__main__':
    main()
