from unittest import main

from astroid import extract_node, parse
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers.duplicate_blank_line_in_block_comment import \
    DuplicateBlankLineInBlockCommentChecker
from ..tests import assert_properties, msg


class TestDuplicateBlankLineInBlockCommentChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateBlankLineInBlockCommentChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_single_empty_line_in_docstring(self):
        node_all = \
            parse(
                '''
                """
                Lorem ipsum

                dolor sit amet.
                """
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_module(node_all)

        node1, node2 = \
            extract_node(
                '''
                class Foo:  #@
                    """
                    Lorem ipsum

                    dolor sit amet.
                    """
                    print()


                def bar():  #@
                    """
                    Lorem ipsum

                    dolor sit amet.
                    """
                    print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)
            self.checker.visit_functiondef(node2)

    def test_multiple_empty_lines_in_docstring(self):
        node_all = \
            parse(
                '''
                """
                Lorem ipsum


                dolor sit amet.
                """
                ''',
            )
        with self.assertAddsMessages(
            msg(DuplicateBlankLineInBlockCommentChecker.MSG, (2, 0, 7, 3), node_all.doc_node),
        ):
            self.checker.visit_module(node_all)

        node1, node2 = \
            extract_node(
                '''
                class Foo:  #@
                    """
                    Lorem ipsum


                    dolor sit amet.
                    """
                    print()


                def bar():  #@
                    """
                    Lorem ipsum


                    dolor sit amet.
                    """
                    print()
                ''',
            )
        with self.assertAddsMessages(
            msg(DuplicateBlankLineInBlockCommentChecker.MSG, (3, 4, 8, 7), node1.doc_node),
            msg(DuplicateBlankLineInBlockCommentChecker.MSG, (13, 4, 18, 7), node2.doc_node),
        ):
            self.checker.visit_classdef(node1)
            self.checker.visit_functiondef(node2)


if __name__ == '__main__':
    main()
