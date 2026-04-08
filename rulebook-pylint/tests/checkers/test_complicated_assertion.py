from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers import ComplicatedAssertionChecker
from testing.messages import msg
from ..asserts import assert_properties


# noinspection PyTypeChecker
class TestComplicatedAssertionChecker(CheckerTestCase):
    CHECKER_CLASS = ComplicatedAssertionChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_targeted_assertion_calls(self):
        node1 = \
            extract_node(
                '''
                class MyTest(TestCase):  #@
                    def test(self):
                        self.assertEqual(1, 2)
                        self.assertNotEqual(1, 2)
                        self.assertIs(1, 2)
                        self.assertIsNot(1, 2)
                        self.assertTrue(False)
                        self.assertIsNone(s)
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)

    def test_generic_assertion_calls(self):
        node1 = \
            extract_node(
                '''
                class MyTest(TestCase):  #@
                    def test(self):
                        self.assertTrue(1 == 2)
                        self.assertFalse(1 != 2)
                        self.assertTrue(1 is 2)
                        self.assertFalse(1 is not 2)
                        self.assertEqual(True, 1 == 2)
                        self.assertEqual(None, s)
                ''',
            )
        with self.assertAddsMessages(
            msg(
                ComplicatedAssertionChecker._MSG,
                (4, 8, 23),
                node1.body[0].body[0].value.func,
                'assertEqual',
            ),
            msg(
                ComplicatedAssertionChecker._MSG,
                (5, 8, 24),
                node1.body[0].body[1].value.func,
                'assertNotEqual',
            ),
            msg(
                ComplicatedAssertionChecker._MSG,
                (6, 8, 23),
                node1.body[0].body[2].value.func,
                'assertIs',
            ),
            msg(
                ComplicatedAssertionChecker._MSG,
                (7, 8, 24),
                node1.body[0].body[3].value.func,
                'assertIsNot',
            ),
            msg(
                ComplicatedAssertionChecker._MSG,
                (8, 8, 24),
                node1.body[0].body[4].value.func,
                'assertTrue',
            ),
            msg(
                ComplicatedAssertionChecker._MSG,
                (9, 8, 24),
                node1.body[0].body[5].value.func,
                'assertIsNone',
            ),
        ):
            self.checker.visit_classdef(node1)


if __name__ == '__main__':
    main()
