from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase
from rulebook_pylint.checkers.member_separator import MemberSeparatorChecker

from ..tests import assert_properties, msg


class TestMemberSeparatorChecker(CheckerTestCase):
    CHECKER_CLASS = MemberSeparatorChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_single_line_members_with_separator(self):
        node_all = \
            parse(
                '''
                class Foo:
                    bar = 1

                    def __init__(self):
                        print()

                    def baz():
                        print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(node_all)
            self.checker.visit_classdef(node_all.body[0])

    def test_single_line_members_without_separator(self):
        node_all = \
            parse(
                '''
                class Foo:
                    bar = 1
                    def __init__(self): print()
                    def baz(): println
                ''',
            )
        with self.assertAddsMessages(
            msg(MemberSeparatorChecker.MSG, (3, 4, 11), args='property'),
            msg(MemberSeparatorChecker.MSG, (4, 24, 31), args='constructor'),
        ):
            self.checker.process_module(node_all)
            self.checker.visit_classdef(node_all.body[0])

    def test_multiline_members_with_separator(self):
        node_all = \
            parse(
                '''
                class Foo:
                    bar = \
                        1 + \
                            2

                    def __init__(self):
                        print()

                    def baz():
                        print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(node_all)
            self.checker.visit_classdef(node_all.body[0])

    def test_multiline_members_without_separator(self):
        node_all = \
            parse(
                '''
                class Foo:
                    bar = \
                        1 + \
                            2
                    def __init__(self):
                        print()
                    def baz():
                        print()
                ''',
            )
        with self.assertAddsMessages(
            msg(MemberSeparatorChecker.MSG, (3, 4, 67), args='property'),
            msg(MemberSeparatorChecker.MSG, (5, 8, 15), args='constructor'),
        ):
            self.checker.process_module(node_all)
            self.checker.visit_classdef(node_all.body[0])

    def test_skip_fields_grouped_together(self):
        node_all = \
            parse(
                '''
                class Foo:
                    bar = 1
                    baz = 1
                    qux = \
                        3 + \
                            4
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(node_all)
            self.checker.visit_classdef(node_all.body[0])

    def test_capture_members_with_comments(self):
        node_all = \
            parse(
                '''
                class Foo:
                    # Comment
                    bar = \
                        1 + \
                            2
                    # Comment
                    def __init__(self):
                        print()
                    # Comment
                    def baz():
                        print()
                ''',
            )
        with self.assertAddsMessages(
            msg(MemberSeparatorChecker.MSG, (4, 4, 67), args='property'),
            msg(MemberSeparatorChecker.MSG, (7, 8, 15), args='constructor'),
        ):
            self.checker.process_module(node_all)
            self.checker.visit_classdef(node_all.body[0])


if __name__ == '__main__':
    main()
