from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.inner_class_position_checker import InnerClassPositionChecker

from .tests import msg


class TestInnerClassPositionChecker(CheckerTestCase):
    CHECKER_CLASS = InnerClassPositionChecker

    def test_inner_class_at_the_bottom(self):
        node1, node2 = \
            extract_node(
                '''
                class Foo: #@
                    def __init__(self):
                        print()

                    def bar(self):
                        print()

                    class Inner: #@
                        print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)
            self.checker.visit_classdef(node2)

    def test_inner_class_before_constructor(self):
        node1, node2 = \
            extract_node(
                '''
                class Foo: #@
                    class Inner: #@
                        print()

                    def __init__(self):
                        print()
                ''',
            )
        with self.assertAddsMessages(
            msg(InnerClassPositionChecker.MSG, (3, 4, 15), node2),
        ):
            self.checker.visit_classdef(node1)
            self.checker.visit_classdef(node2)


if __name__ == '__main__':
    main()
