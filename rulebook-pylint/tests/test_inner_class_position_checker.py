from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.inner_class_position_checker import InnerClassPositionChecker

from .tests import assert_properties, msg


class TestInnerClassPositionChecker(CheckerTestCase):
    CHECKER_CLASS = InnerClassPositionChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_inner_classes_at_the_bottom(self):
        node1, node2, node3 = \
            extract_node(
                '''
                class Foo:  #@
                    def __init__(self):
                        print()

                    def bar(self):
                        print()

                    class Inner:  #@
                        print()

                    class AnotherInner:  #@
                        print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)
            self.checker.visit_classdef(node2)
            self.checker.visit_classdef(node3)

    def test_inner_classes_before_members(self):
        node1, node2, node3 = \
            extract_node(
                '''
                class Foo:  #@
                    class Inner:  #@
                        print()

                    bar = 0

                    class AnotherInner:  #@
                        print()

                    def baz():
                        print()
                ''',
            )
        with self.assertAddsMessages(
            msg(InnerClassPositionChecker.MSG, (3, 4, 15), node2),
            msg(InnerClassPositionChecker.MSG, (8, 4, 22), node3),
        ):
            self.checker.visit_classdef(node1)
            self.checker.visit_classdef(node2)
            self.checker.visit_classdef(node3)


if __name__ == '__main__':
    main()
