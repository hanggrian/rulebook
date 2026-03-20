from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers import ComplicatedAssignmentChecker
from ..tests import assert_properties, msg


# noinspection PyTypeChecker
class TestAbbreviationAsWordChecker(CheckerTestCase):
    CHECKER_CLASS = ComplicatedAssignmentChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_shorthand_assignments(self):
        pass

    def test_complicated_assignments(self):
        node1, node2, node3, node4, node5 = \
            extract_node(
                '''
                def foo():
                    bar = 0
                    bar = bar + 1  #@
                    bar = bar - 1  #@
                    bar = bar * 1  #@
                    bar = bar / 1  #@
                    bar = bar % 1  #@
                ''',
            )
        with self.assertAddsMessages(
            msg(ComplicatedAssignmentChecker._MSG, (4, 4, 17), node1, '+='),
            msg(ComplicatedAssignmentChecker._MSG, (5, 4, 17), node2, '-='),
            msg(ComplicatedAssignmentChecker._MSG, (6, 4, 17), node3, '*='),
            msg(ComplicatedAssignmentChecker._MSG, (7, 4, 17), node4, '/='),
            msg(ComplicatedAssignmentChecker._MSG, (8, 4, 17), node5, '%='),
        ):
            self.checker.visit_assign(node1)
            self.checker.visit_assign(node2)
            self.checker.visit_assign(node3)
            self.checker.visit_assign(node4)
            self.checker.visit_assign(node5)

    def test_target_leftmost_operator(self):
        node1 = \
            extract_node(
                '''
                def foo():
                    bar = 0
                    bar = bar + 1 - 2 * 3 / 4 % 5  #@
                ''',
            )
        with self.assertAddsMessages(
            msg(ComplicatedAssignmentChecker._MSG, (4, 4, 33), node1, '+='),
        ):
            self.checker.visit_assign(node1)


if __name__ == '__main__':
    main()
