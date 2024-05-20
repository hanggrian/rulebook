import unittest

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.object_structural_comparison_checker import ObjectStructuralComparisonChecker

from .testing import msg


# pylint: disable=missing-class-docstring
class TestObjectStructuralComparisonChecker(CheckerTestCase):
    CHECKER_CLASS = ObjectStructuralComparisonChecker

    def test_structural_equalities(self):
        def1, def2 = extract_node(
            '''
            if foo == bar: #@
                print()
            elif foo != bar: #@
                print()
            ''',
        )
        with self.assertNoMessages():
            self.checker.visit_compare(def1)
            self.checker.visit_compare(def2)

    def test_referential_equalities(self):
        def1, def2 = extract_node(
            '''
            if foo is bar: #@
                print()
            elif foo is not bar: #@
                print()
            ''',
        )
        with self.assertAddsMessages(
            msg(ObjectStructuralComparisonChecker.MSG_EQ, (2, 3, 13), def1.test),
            msg(ObjectStructuralComparisonChecker.MSG_NEQ, (4, 5, 19), def2.test),
        ):
            self.checker.visit_compare(def1)
            self.checker.visit_compare(def2)

    def test_assigned_condition(self):
        def1 = extract_node(
            '''
            baz = foo is bar #@
            ''',
        )
        with self.assertAddsMessages(
            msg(ObjectStructuralComparisonChecker.MSG_EQ, (2, 6, 16), def1.value),
        ):
            self.checker.visit_compare(def1)

    def test_skip_comparing_none(self):
        def1, def2 = extract_node(
            '''
            if foo is None: #@
                print()

            baz = None is bar #@
            ''',
        )
        with self.assertNoMessages():
            self.checker.visit_compare(def1)
            self.checker.visit_compare(def2)


if __name__ == '__main__':
    unittest.main()
