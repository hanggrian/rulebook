from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.illegal_class_final_name import IllegalClassFinalNameChecker

from .tests import assert_properties, msg


class TestIllegalClassFinalNameChecker(CheckerTestCase):
    CHECKER_CLASS = IllegalClassFinalNameChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_meaningful_class_names(self):
        node1 = \
            extract_node(
                '''
                class Spaceship:  #@
                    print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)

    def test_meaningless_class_names(self):
        node1 = \
            extract_node(
                '''
                class SpaceshipManager:  #@
                    print()
                ''',
            )
        with self.assertAddsMessages(
            msg(IllegalClassFinalNameChecker.MSG_ALL, (2, 0, 22), node1, 'Manager'),
        ):
            self.checker.visit_classdef(node1)

    def test_utility_class_found(self):
        node1 = \
            extract_node(
                '''
                class SpaceshipUtil:  #@
                    print()
                ''',
            )
        with self.assertAddsMessages(
            msg(IllegalClassFinalNameChecker.MSG_UTIL, (2, 0, 19), node1, 'Spaceships'),
        ):
            self.checker.visit_classdef(node1)


if __name__ == '__main__':
    main()
