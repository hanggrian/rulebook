from unittest import main

from astroid import extract_node, parse
from pylint.testutils import CheckerTestCase
from rulebook_pylint.block_comment_line_joining_checker import BlockCommentLineJoiningChecker

from .tests import msg


class TestBlockCommentLineJoiningChecker(CheckerTestCase):
    CHECKER_CLASS = BlockCommentLineJoiningChecker

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
            msg(BlockCommentLineJoiningChecker.MSG, (2, 0, 7, 3), node_all.doc_node),
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
            msg(BlockCommentLineJoiningChecker.MSG, (3, 4, 8, 7), node1.doc_node),
            msg(BlockCommentLineJoiningChecker.MSG, (12, 4, 17, 7), node2.doc_node),
        ):
            self.checker.visit_classdef(node1)
            self.checker.visit_functiondef(node2)


if __name__ == '__main__':
    main()
