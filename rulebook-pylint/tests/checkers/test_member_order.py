from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.checkers.member_order import MemberOrderChecker

from ..tests import assert_properties, msg


class TestMemberOrderChecker(CheckerTestCase):
    CHECKER_CLASS = MemberOrderChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_correct_organization(self):
        node1 = \
            extract_node(
                '''
                class Foo:  #@
                    bar = 0

                    def __init__(self):
                        pass

                    def baz(self):
                        pass

                    @staticmethod
                    def qux():
                        pass
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)

    def test_property_after_constructor(self):
        node1, node2 = \
            extract_node(
                '''
                class Foo:  #@
                    def __init__(self):
                        pass

                    bar = 0  #@
                ''',
            )
        with self.assertAddsMessages(
            msg(MemberOrderChecker.MSG, (6, 4, 7), node2.targets[0], ('property', 'constructor')),
        ):
            self.checker.visit_classdef(node1)

    def test_constructor_after_function(self):
        node1, node2 = \
            extract_node(
                '''
                class Foo:  #@
                    def baz():
                        pass

                    def __init__(self):  #@
                        pass
                ''',
            )
        with self.assertAddsMessages(
            msg(MemberOrderChecker.MSG, (6, 4, 16), node2, ('constructor', 'function')),
        ):
            self.checker.visit_classdef(node1)

    def test_function_after_static_member(self):
        node1, node2 = \
            extract_node(
                '''
                class Foo:  #@
                    @staticmethod
                    def qux():
                        pass

                    def baz():  #@
                        pass
                ''',
            )
        with self.assertAddsMessages(
            msg(MemberOrderChecker.MSG, (7, 4, 11), node2, ('function', 'static member')),
        ):
            self.checker.visit_classdef(node1)


if __name__ == '__main__':
    main()
