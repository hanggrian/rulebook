from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.member_order import MemberOrderChecker

from .tests import assert_properties, msg


class TestMemberOrderChecker(CheckerTestCase):
    CHECKER_CLASS = MemberOrderChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_correct_organization(self):
        node1 = \
            extract_node(
                '''
                class Foo:  #@
                    def __init__(self):
                        print()

                    def baz(self):
                        print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)

    def test_constructor_after_function(self):
        node1, node2 = \
            extract_node(
                '''
                class Foo:  #@
                    def bar():
                        print()

                    def __init__(self):  #@
                        print()
                ''',
            )
        with self.assertAddsMessages(
            msg(MemberOrderChecker.MSG, (6, 4, 16), node2, ('constructor', 'function')),
        ):
            self.checker.visit_classdef(node1)

    def test_skip_static_members(self):
        node1 = \
            extract_node(
                '''
                class Foo:  #@
                    @staticmethod
                    def bar():
                        print()

                    def __init__(self):
                        print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)


if __name__ == '__main__':
    main()
