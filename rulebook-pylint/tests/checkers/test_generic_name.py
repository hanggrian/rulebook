from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers.generic_name import GenericNameChecker
from ..tests import assert_properties, msg


# noinspection PyTypeChecker
class TestGenericNameChecker(CheckerTestCase):
    CHECKER_CLASS = GenericNameChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_correct_generic_name_in_class_alike(self):
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

    def test_incorrect_generic_name_in_class_alike(self):
        node1 = \
            extract_node(
                '''
                XA = TypeVar('XA')  #@


                class MyClass(T):
                    print()
                ''',
            )
        with self.assertAddsMessages(
            msg(GenericNameChecker.MSG, (2, 0, 2), node1.targets[0]),
        ):
            self.checker.visit_assign(node1)


if __name__ == '__main__':
    main()
