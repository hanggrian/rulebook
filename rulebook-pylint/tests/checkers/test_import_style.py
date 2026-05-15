from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers import ImportStyleChecker
from testing.messages import msg
from ..asserts import assert_properties


# noinspection PyTypeChecker
class TestImportStyleChecker(CheckerTestCase):
    CHECKER_CLASS = ImportStyleChecker

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
            self.checker.visit_importfrom(node_all.body[0])

    def test_ungrouped_imports(self):
        node_all = \
            parse(
                '''
                from foo import bar
                from foo import baz
                ''',
            )
        with self.assertAddsMessages(
            msg(ImportStyleChecker._MSG_GROUP, (3, 0, 19)),
        ):
            self.checker.process_module(node_all)
            self.checker.visit_importfrom(node_all.body[0])
            self.checker.visit_importfrom(node_all.body[1])

    def test_track_regular_imports(self):
        node_all = \
            parse(
                '''
                import foo
                from foo import baz
                ''',
            )
        with self.assertAddsMessages(
            msg(ImportStyleChecker._MSG_GROUP, (3, 0, 19)),
        ):
            self.checker.process_module(node_all)
            self.checker.visit_import(node_all.body[0])
            self.checker.visit_importfrom(node_all.body[1])

    def test_backlash_continuation_imports(self):
        node_all = \
            parse(
                # pylint: disable=import-style, useless-suppression
                '''
                from foo import \\
                    bar, \\
                    baz
                ''',
            )
        with self.assertAddsMessages(
            msg(ImportStyleChecker._MSG_PARENTHESES, 2),
        ):
            self.checker.process_module(node_all)
            self.checker.visit_importfrom(node_all.body[0])


if __name__ == '__main__':
    main()
