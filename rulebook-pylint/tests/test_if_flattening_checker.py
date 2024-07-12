from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.if_flattening_checker import IfFlatteningChecker

from .tests import msg


class TestIfFlatteningChecker(CheckerTestCase):
    CHECKER_CLASS = IfFlatteningChecker

    def test_one_statement_in_if_statement(self):
        node1 = \
            extract_node(
                '''
                def foo():  #@
                    if True:
                        print()
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
                        bar()
                        baz()
                ''',
            )
        with self.assertAddsMessages(
            msg(IfFlatteningChecker.MSG, (3, 4, 5, 13), node1.body[0]),
        ):
            self.checker.visit_functiondef(node1)

    def test_do_not_invert_when_there_is_else(self):
        node1 = \
            extract_node(
                '''
                def foo():  #@
                    if True:
                        bar()
                        baz()
                    else:
                        baz()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(node1)


if __name__ == '__main__':
    main()
