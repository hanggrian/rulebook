from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.exception_extending_checker import ExceptionExtendingChecker

from .tests import msg


class TestExceptionExtendingChecker(CheckerTestCase):
    CHECKER_CLASS = ExceptionExtendingChecker

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
            msg(ExceptionExtendingChecker.MSG, (2, 10, 23), node=node1.bases[0]),
        ):
            self.checker.visit_classdef(node1)


if __name__ == '__main__':
    main()
