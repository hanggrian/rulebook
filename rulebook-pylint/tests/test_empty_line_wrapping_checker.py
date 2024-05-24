from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase
from rulebook_pylint.empty_line_wrapping_checker import EmptyLineWrappingChecker

from .tests import msg

class TestEmptyLineWrappingChecker(CheckerTestCase):
    CHECKER_CLASS = EmptyLineWrappingChecker

    def test_single_empty_line(self):
        def_all = \
            parse(
                '''
                foo = 0

                bar = 1
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(def_all)

    def test_double_empty_line(self):
        def_all = \
            parse(
                '''
                foo = 0


                bar = 1
                ''',
            )
        with self.assertAddsMessages(msg(EmptyLineWrappingChecker.MSG, 4)):
            self.checker.process_module(def_all)

if __name__ == '__main__':
    main()
