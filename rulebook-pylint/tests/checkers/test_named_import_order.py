from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers.named_import_order import NamedImportOrderChecker
from ..tests import assert_properties, msg


# noinspection PyTypeChecker
class TestNamedImportOrderChecker(CheckerTestCase):
    CHECKER_CLASS = NamedImportOrderChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_sorted_named_imports(self):
        node1 = \
            extract_node(
                '''
                from mymodule import bar, baz, foo  #@
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_importfrom(node1)

    def test_unsorted_named_imports(self):
        node1 = \
            extract_node(
                '''
                from mymodule import foo, baz, bar  #@
                ''',
            )
        with self.assertAddsMessages(
            msg(NamedImportOrderChecker._MSG, (2, 0, 2, 34), node1, ('baz', 'foo')),
            msg(NamedImportOrderChecker._MSG, (2, 0, 2, 34), node1, ('bar', 'baz')),
        ):
            self.checker.visit_importfrom(node1)


if __name__ == '__main__':
    main()
