from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers import ConfusingAssertionChecker
from testing.messages import msg
from ..asserts import assert_properties


# noinspection PyTypeChecker
class TestConfusingAssertionChecker(CheckerTestCase):
    CHECKER_CLASS = ConfusingAssertionChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_positive_assertion_calls(self):
        node1 = \
            extract_node(
                '''
                class MyTest(TestCase):  #@
                    def test(self):
                        self.assertTrue(condition)
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)

    def test_negative_assertion_calls(self):
        node1 = \
            extract_node(
                '''
                class MyTest(TestCase):  #@
                    def test(self):
                        self.assertTrue(not condition)
                ''',
            )
        with self.assertAddsMessages(
            msg(
                ConfusingAssertionChecker._MSG,
                (4, 8, 23),
                node1.body[0].body[0].value.func,
                'assertFalse',
            ),
        ):
            self.checker.visit_classdef(node1)


if __name__ == '__main__':
    main()
