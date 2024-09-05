from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.generics_name_allowing_checker import GenericsNameAllowingChecker

from .tests import assert_properties, msg


class TestGenericsNameAllowingChecker(CheckerTestCase):
    CHECKER_CLASS = GenericsNameAllowingChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_common_generic_type_in_class_alike(self):
        node1 = \
            extract_node(
                '''
                T = TypeVar('T')  #@

                class MyClass(T):
                    print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_assign(node1)

    def test_uncommon_generic_type_in_class_alike(self):
        node2 = \
            extract_node(
                '''
                X = TypeVar('X')  #@

                class MyClass(T):
                    print()
                ''',
            )
        with self.assertAddsMessages(
            msg(GenericsNameAllowingChecker.MSG, (2, 0, 1), node2.targets[0], 'X'),
        ):
            self.checker.visit_assign(node2)


if __name__ == '__main__':
    main()
