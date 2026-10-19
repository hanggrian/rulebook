from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers import BlockCommentTrimmingChecker
from testing.messages import msg
from ..asserts import assert_properties


# noinspection PyTypeChecker
class TestBlockCommentTrimmingChecker(CheckerTestCase):
    CHECKER_CLASS = BlockCommentTrimmingChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_block_comment_without_initial_and_final_newline(self):
        node1 = \
            extract_node(
                '''
                class Foo:  #@
                    """
                    Lorem ipsum
                    """
                    print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)

    def test_block_comment_with_initial_and_final_newline(self):
        node1 = \
            extract_node(
                '''
                class Foo:  #@
                    """

                    Lorem ipsum

                    """
                    print()
                ''',
            )
        with self.assertAddsMessages(
            msg(BlockCommentTrimmingChecker._MSG_FIRST, (3, 4, 7, 7), node1.doc_node),
            msg(BlockCommentTrimmingChecker._MSG_LAST, (7, 4, 7, 7), node1.doc_node),
        ):
            self.checker.visit_classdef(node1)

    def test_skip_single_line_block_comment(self):
        node1 = \
            extract_node(
                '''
                class Foo:  #@
                    """Lorem ipsum"""
                    print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)

    def test_skip_blank_comment(self):
        node1 = \
            extract_node(
                '''
                class Foo:  #@
                    """
                    """
                    print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)


if __name__ == '__main__':
    main()
