from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase
from rulebook_pylint.file_size import FileSizeChecker

from .tests import assert_properties, msg


class TestFileSizeChecker(CheckerTestCase):
    CHECKER_CLASS = FileSizeChecker
    CHECKER_CLASS.options[0][1]['default'] = 3

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

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
        with self.assertAddsMessages(msg(FileSizeChecker.MSG, 0, args=3)):
            self.checker.process_module(node_all)


if __name__ == '__main__':
    main()
