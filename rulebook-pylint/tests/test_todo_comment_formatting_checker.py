from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase
from rulebook_pylint.todo_comment_formatting_checker import TodoCommentFormattingChecker

from .tests import assert_properties, msg


# pylint: disable=todo-comment-styling-keyword,todo-comment-styling-separator
class TestTodoCommentFormattingChecker(CheckerTestCase):
    CHECKER_CLASS = TodoCommentFormattingChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_uppercase_todo_comments(self):
        node_all = \
            parse(
                '''
                # TODO add tests
                # FIXME fix bug
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(node_all)

    def test_lowercase_todo_comments(self):
        node_all = \
            parse(
                '''
                # todo add tests
                # fixme fix bug
                ''',
            )
        with self.assertAddsMessages(
            msg(TodoCommentFormattingChecker.MSG_KEYWORD, 2, args='todo'),
            msg(TodoCommentFormattingChecker.MSG_KEYWORD, 3, args='fixme'),
        ):
            self.checker.process_module(node_all)

    def test_unknown_todo_comments(self):
        node_all = \
            parse(
                '''
                # TODO: add tests
                # FIXME1 fix bug
                ''',
            )
        with self.assertAddsMessages(
            msg(TodoCommentFormattingChecker.MSG_SEPARATOR, 2, args=':'),
            msg(TodoCommentFormattingChecker.MSG_SEPARATOR, 3, args='1'),
        ):
            self.checker.process_module(node_all)

    def test_todo_keyword_mid_sentence(self):
        node_all = \
            parse(
                '''
                # Untested. Todo: add tests.
                ''',
            )
        with self.assertAddsMessages(
            msg(TodoCommentFormattingChecker.MSG_KEYWORD, 2, args='Todo'),
            msg(TodoCommentFormattingChecker.MSG_SEPARATOR, 2, args=':'),
        ):
            self.checker.process_module(node_all)


if __name__ == '__main__':
    main()
