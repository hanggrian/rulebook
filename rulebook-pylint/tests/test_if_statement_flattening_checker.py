from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.if_statement_flattening_checker import IfStatementFlatteningChecker

from .tests import msg

class TestIfStatementFlatteningChecker(CheckerTestCase):
    CHECKER_CLASS = IfStatementFlatteningChecker

    def test_empty_then_statement(self):
        def1 = \
            extract_node(
                '''
                def foo(): #@
                    if True:
                        print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(def1)

    def test_inverted_if_statement(self):
        def1 = \
            extract_node(
                '''
                def foo(): #@
                    if True:
                        return
                    bar()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(def1)

    def test_only_1_line_in_if_statement(self):
        def1 = \
            extract_node(
                '''
                def foo(): #@
                    if True:
                        bar()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(def1)

    def test_at_least_2_lines_in_if_statement(self):
        def1 = \
            extract_node(
                '''
                def foo(): #@
                    if True:
                        bar()
                        baz()
                ''',
            )
        with self.assertAddsMessages(
            msg(IfStatementFlatteningChecker.MSG, (3, 4, 5, 13), def1.body[0]),
        ):
            self.checker.visit_functiondef(def1)

    def test_if_statement_with_else_if(self):
        def1 = \
            extract_node(
                '''
                def foo(): #@
                    if True:
                        bar()
                        baz()
                    elif False:
                        baz()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(def1)

    def test_if_statement_with_else(self):
        def1 = \
            extract_node(
                '''
                def foo(): #@
                    if True:
                        bar()
                        baz()
                    else:
                        baz()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(def1)

if __name__ == '__main__':
    main()
