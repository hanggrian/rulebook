import unittest

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from generics_naming_checker import GenericsNamingChecker
from testing import msg


# pylint: disable=missing-class-docstring
class TestGenericsNamingChecker(CheckerTestCase):
    CHECKER_CLASS = GenericsNamingChecker

    def test_common_generic_type_in_class_alike(self):
        def1 = extract_node(
            '''
            T = TypeVar('T') #@

            class MyClass(T):
                stub = 1
            ''',
        )
        with self.assertNoMessages():
            self.checker.visit_assign(def1)

    def test_uncommon_generic_type_in_class_alike(self):
        def1 = extract_node(
            '''
            X = TypeVar('X') #@

            class MyClass(T):
                stub = 1
            ''',
        )
        with self.assertAddsMessages(msg(GenericsNamingChecker, def1, (2, 0), 'X')):
            self.checker.visit_assign(def1)


if __name__ == '__main__':
    unittest.main()
