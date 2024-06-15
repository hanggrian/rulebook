from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.operand_structural_equality_checker import OperandStructuralEqualityChecker

from .tests import msg


class TestOperandStructuralEqualityChecker(CheckerTestCase):
    CHECKER_CLASS = OperandStructuralEqualityChecker

    def test_structural_equalities(self):
        node1, node2 = \
            extract_node(
                '''
                if foo == bar: #@
                    print()
                elif foo != bar: #@
                    print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_compare(node1)
            self.checker.visit_compare(node2)

    def test_referential_equalities(self):
        node1, node2 = \
            extract_node(
                '''
                if foo is bar: #@
                    print()
                elif foo is not bar: #@
                    print()
                ''',
            )
        with self.assertAddsMessages(
            msg(OperandStructuralEqualityChecker.MSG_EQ, (2, 3, 13), node1.test),
            msg(OperandStructuralEqualityChecker.MSG_NEQ, (4, 5, 19), node2.test),
        ):
            self.checker.visit_compare(node1)
            self.checker.visit_compare(node2)

    def test_assigned_condition(self):
        node1 = \
            extract_node(
                '''
                baz = foo is bar #@
                ''',
            )
        with self.assertAddsMessages(
            msg(OperandStructuralEqualityChecker.MSG_EQ, (2, 6, 16), node1.value),
        ):
            self.checker.visit_compare(node1)

    def test_skip_comparing_none(self):
        node1, node2 = \
            extract_node(
                '''
                if foo is None: #@
                    print()

                baz = None is bar #@
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_compare(node1)
            self.checker.visit_compare(node2)


if __name__ == '__main__':
    main()
