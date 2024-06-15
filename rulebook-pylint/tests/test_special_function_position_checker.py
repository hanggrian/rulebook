from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.special_function_position_checker import SpecialFunctionPositionChecker

from .tests import msg


class TestSpecialFunctionPositionChecker(CheckerTestCase):
    CHECKER_CLASS = SpecialFunctionPositionChecker

    def test_overridden_function_at_the_bottom(self):
        node1, node2 = \
            extract_node(
                '''
                class Foo:
                    def bar(): #@
                        print()

                    def __str__(self): #@
                        return 'baz'
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(node1)
            self.checker.visit_functiondef(node2)

    def test_constructor_after_function(self):
        node1, node2 = \
            extract_node(
                '''
                class Foo:
                    def __str__(self): #@
                        return 'baz'

                    def bar(): #@
                        print()
                ''',
            )
        with self.assertAddsMessages(
            msg(SpecialFunctionPositionChecker.MSG, (3, 4, 15), node1, '__str__'),
        ):
            self.checker.visit_functiondef(node1)
            self.checker.visit_functiondef(node2)

    def test_grouping_overridden_functions(self):
        node1, node2, node3 = \
            extract_node(
                '''
                class Foo:
                    def __str__(self): #@
                        return 'baz'

                    def __hash__(self): #@
                        return 0

                    def __eq__(self, other): #@
                        return False
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(node1)
            self.checker.visit_functiondef(node2)
            self.checker.visit_functiondef(node3)


if __name__ == '__main__':
    main()
