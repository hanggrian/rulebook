from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.if_else_flattening_checker import IfElseFlatteningChecker

from .tests import assert_properties, msg


class TestIfElseFlatteningChecker(CheckerTestCase):
    CHECKER_CLASS = IfElseFlatteningChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_empty_or_one_statement_in_if_statement(self):
        node1 = \
            extract_node(
                '''
                def foo():  #@
                    if True:
                        baz()

                def bar():
                    if True:
                        baz()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(node1)

    def test_invert_if_with_two_statements(self):
        node1 = \
            extract_node(
                '''
                def foo():  #@
                    if True:
                        baz()
                        baz()
                ''',
            )
        with self.assertAddsMessages(
            msg(IfElseFlatteningChecker.MSG_INVERT, (3, 4, 5, 13), node1.body[0]),
        ):
            self.checker.visit_functiondef(node1)

    def test_lift_else_when_there_is_no_else_if(self):
        node1 = \
            extract_node(
                '''
                def foo():  #@
                    if True:
                        bar()
                    else:
                        baz()
                ''',
            )
        with self.assertAddsMessages(
            msg(IfElseFlatteningChecker.MSG_LIFT, (6, 8, 6, 13), node1.body[0].orelse[0]),
        ):
            self.checker.visit_functiondef(node1)

    def test_skip_else_if(self):
        node1 = \
            extract_node(
                '''
                def foo():  #@
                    if True:
                        baz()
                    elif False:
                        baz()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(node1)

    def test_capture_trailing_non_ifs(self):
        node1 = \
            extract_node(
                '''
                def foo():  #@
                    if True:
                        baz()
                        baz()

                    # Lorem ipsum.
                ''',
            )
        with self.assertAddsMessages(
            msg(IfElseFlatteningChecker.MSG_INVERT, (3, 4, 5, 13), node1.body[0]),
        ):
            self.checker.visit_functiondef(node1)


if __name__ == '__main__':
    main()
