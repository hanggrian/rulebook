from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers.required_generics_name import RequiredGenericsNameChecker
from ..tests import assert_properties, msg


class TestRequiredGenericsNameChecker(CheckerTestCase):
    CHECKER_CLASS = RequiredGenericsNameChecker

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
        node1 = \
            extract_node(
                '''
                X = TypeVar('X')  #@


                class MyClass(T):
                    print()
                ''',
            )
        with self.assertAddsMessages(
            msg(RequiredGenericsNameChecker.MSG, (2, 0, 1), node1.targets[0], 'E, K, N, T, V'),
        ):
            self.checker.visit_assign(node1)


if __name__ == '__main__':
    main()
