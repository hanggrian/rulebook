from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase
from testing.messages import msg

from rulebook_pylint.checkers import NoDuplicateImportChecker
from ..asserts import assert_properties


# noinspection PyTypeChecker
class TestNoDuplicateImportChecker(CheckerTestCase):
    CHECKER_CLASS = NoDuplicateImportChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_unduplicated_imports(self):
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

    def test_duplicated_imports(self):
        node_all = \
            parse(
                '''
                from foo import bar
                from foo import baz
                ''',
            )
        with self.assertAddsMessages(
            msg(NoDuplicateImportChecker._MSG, (3, 0, 19)),
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
            msg(NoDuplicateImportChecker._MSG, (3, 0, 19)),
        ):
            self.checker.process_module(node_all)
            self.checker.visit_import(node_all.body[0])
            self.checker.visit_importfrom(node_all.body[1])


if __name__ == '__main__':
    main()
