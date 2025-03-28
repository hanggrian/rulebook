from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase
from rulebook_pylint.duplicate_blank_line import DuplicateBlankLineChecker

from .tests import assert_properties, msg


class TestDuplicateBlankLineChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateBlankLineChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_single_empty_line(self):
        node_all = \
            parse(
                '''

                def foo():

                    pass
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(node_all)

    def test_multiple_empty_lines(self):
        node_all = \
            parse(
                '''


                def foo():



                    pass
                ''',
            )
        with self.assertAddsMessages(
            msg(DuplicateBlankLineChecker.MSG, 3),
            msg(DuplicateBlankLineChecker.MSG, 7),
        ):
            self.checker.process_module(node_all)


if __name__ == '__main__':
    main()
