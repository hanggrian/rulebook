from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers.internal_error import InternalErrorChecker
from ..tests import assert_properties, msg


# noinspection PyTypeChecker
class TestInternalErrorChecker(CheckerTestCase):
    CHECKER_CLASS = InternalErrorChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_extend_user_exceptions(self):
        node1 = \
            extract_node(
                '''
                class Foo(Exception):  #@
                    pass
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)

    def test_extend_non_user_exceptions(self):
        node1 = \
            extract_node(
                '''
                class Foo(BaseException):  #@
                    pass
                ''',
            )
        with self.assertAddsMessages(
            msg(InternalErrorChecker._MSG, (2, 10, 23), node=node1.bases[0]),
        ):
            self.checker.visit_classdef(node1)


if __name__ == '__main__':
    main()
