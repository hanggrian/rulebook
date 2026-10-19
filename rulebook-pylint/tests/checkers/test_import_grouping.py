from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase
from testing.messages import msg

from rulebook_pylint.checkers import ImportGroupingChecker
from ..asserts import assert_properties


# noinspection PyTypeChecker
class TestImportGroupingChecker(CheckerTestCase):
    CHECKER_CLASS = ImportGroupingChecker

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
                # pylint: disable=import-grouping
                '''
                from foo import \\
                    bar, \\
                    baz
                ''',
            )
        with self.assertAddsMessages(
            msg(ImportGroupingChecker._MSG, 2),
        ):
            self.checker.process_module(node_all)


if __name__ == '__main__':
    main()
