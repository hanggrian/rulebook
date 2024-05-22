from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.generics_common_naming_checker import GenericsCommonNamingChecker

from .testing import msg

class TestGenericsCommonNamingChecker(CheckerTestCase):
    CHECKER_CLASS = GenericsCommonNamingChecker

    def test_common_generic_type_in_class_alike(self):
        def1 = \
            extract_node(
                '''
                T = TypeVar('T') #@

                class MyClass(T):
                    print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_assign(def1)

    def test_uncommon_generic_type_in_class_alike(self):
        def1 = \
            extract_node(
                '''
                X = TypeVar('X') #@

                class MyClass(T):
                    print()
                ''',
            )
        with self.assertAddsMessages(
            msg(GenericsCommonNamingChecker.MSG, (2, 0, 1), def1.targets[0], 'X'),
        ):
            self.checker.visit_assign(def1)

if __name__ == '__main__':
    main()
