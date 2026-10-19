from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase
from testing.messages import msg

from rulebook_pylint.checkers import ParenthesesInImportChecker
from ..asserts import assert_properties


# noinspection PyTypeChecker
class TestParenthesesInImportChecker(CheckerTestCase):
    CHECKER_CLASS = ParenthesesInImportChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_grouped_imports(self):
        node_all = \
            parse(
                '''
                from foo import (
                    bar,
                    baz,
                )
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(node_all)

    def test_backlash_continuation_imports(self):
        node_all = \
            parse(
                # pylint: disable=parentheses-in-import
                '''
                from foo import \\
                    bar, \\
                    baz
                ''',
            )
        with self.assertAddsMessages(
            msg(ParenthesesInImportChecker._MSG, 2),
        ):
            self.checker.process_module(node_all)


if __name__ == '__main__':
    main()
