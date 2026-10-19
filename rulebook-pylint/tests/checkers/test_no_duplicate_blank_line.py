from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers import NoDuplicateBlankLineChecker
from testing.messages import msg
from ..asserts import assert_properties


# noinspection PyTypeChecker
class TestNoDuplicateBlankLineChecker(CheckerTestCase):
    CHECKER_CLASS = NoDuplicateBlankLineChecker

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

    # pylint: disable=no-duplicate-blank-line
    def test_multiple_empty_lines(self):
        node_all = \
            parse(
                '''


                def foo():



                    pass
                ''',
            )
        with self.assertAddsMessages(
            msg(NoDuplicateBlankLineChecker._MSG, 3),
            msg(NoDuplicateBlankLineChecker._MSG, 7),
        ):
            self.checker.process_module(node_all)


if __name__ == '__main__':
    main()
