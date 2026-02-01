from unittest import main

from astroid import extract_node, parse
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers.short_block_comment_clip import ShortBlockCommentClipChecker
from ..tests import assert_properties, msg


class TestShortBlockCommentClipChecker(CheckerTestCase):
    CHECKER_CLASS = ShortBlockCommentClipChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_correct_block_comments(self):
        node1, node2 = \
            extract_node(
                '''
                """Short comment."""
                class Foo:  #@
                    pass

                """
                Very long
                comment.
                """
                def bar():  #@
                    pass
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)
            self.checker.visit_functiondef(node2)

    def test_multi_line_block_comment_that_can_be_converted_into_single_line(self):
        node_all = \
            parse(
                '''
                """
                Short comment.
                """
                ''',
            )
        with self.assertAddsMessages(
            msg(ShortBlockCommentClipChecker.MSG, (2, 0, 4, 3), node_all.doc_node),
        ):
            self.checker.visit_module(node_all)

    def test_skip_tagged_block_comment(self):
        node_all = \
            parse(
                '''
                def foo(
                    # Lorem ipsum.
                    bar: int,
                    # Lorem ipsum.
                ):
                    baz = {
                        # Lorem ipsum.
                        1,
                        # Lorem ipsum.
                    }
                    qux = [
                        # Lorem ipsum.
                        3,
                        # Lorem ipsum.
                    ]
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_module(node_all)


if __name__ == '__main__':
    main()
