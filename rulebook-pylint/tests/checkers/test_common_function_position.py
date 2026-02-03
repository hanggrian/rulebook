from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers.common_function_position import CommonFunctionPositionChecker
from ..tests import assert_properties, msg


# noinspection PyTypeChecker
class TestCommonFunctionPositionChecker(CheckerTestCase):
    CHECKER_CLASS = CommonFunctionPositionChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_special_function_at_the_bottom(self):
        node1, node2, node3, node4 = \
            extract_node(
                '''
                class Foo:
                    def bar():  #@
                        print()

                    def baz():  #@
                        print()

                    def __str__(self):  #@
                        return 'foo'

                    def __hash__(self):  #@
                        return 0
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(node1)
            self.checker.visit_functiondef(node2)
            self.checker.visit_functiondef(node3)
            self.checker.visit_functiondef(node4)

    def test_special_function_not_at_the_function(self):
        node1, node2, node3, node4 = \
            extract_node(
                '''
                class Foo:
                    def __str__(self):  #@
                        return 'foo'

                    def bar():  #@
                        print()

                    def __hash__(self):  #@
                        return 0

                    def baz():  #@
                        print()
                ''',
            )
        with self.assertAddsMessages(
            msg(CommonFunctionPositionChecker.MSG, (3, 4, 15), node1, '__str__'),
            msg(CommonFunctionPositionChecker.MSG, (9, 4, 16), node3, '__hash__'),
        ):
            self.checker.visit_functiondef(node1)
            self.checker.visit_functiondef(node2)
            self.checker.visit_functiondef(node3)
            self.checker.visit_functiondef(node4)

    def test_grouped_overridden_functions(self):
        node1, node2, node3 = \
            extract_node(
                '''
                class Foo:
                    def __str__(self):  #@
                        return 'baz'

                    def __hash__(self):  #@
                        return 0

                    def __eq__(self, other):  #@
                        return False
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(node1)
            self.checker.visit_functiondef(node2)
            self.checker.visit_functiondef(node3)

    def test_skip_static_members(self):
        node1, node2 = \
            extract_node(
                '''
                class Foo:
                    def __str__(self):  #@
                        return 'baz'

                    @staticmethod
                    def bar():  #@
                        print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(node1)
            self.checker.visit_functiondef(node2)


if __name__ == '__main__':
    main()
