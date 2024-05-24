from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.constructor_position_checker import ConstructorPositionChecker

from .tests import msg

class TestConstructorPositionChecker(CheckerTestCase):
    CHECKER_CLASS = ConstructorPositionChecker

    def test_properties_initializers_constructors_and_methods(self):
        def1 = \
            extract_node(
                '''
                class Foo:
                    bar = 0

                    def __init__(self): #@
                        print()

                    def baz(self):
                        print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(def1)

    def test_property_after_constructor(self):
        def1, def2 = \
            extract_node(
                '''
                class Foo:
                    def __init__(self): #@
                        print()

                    bar = 0 #@
                ''',
            )
        with self.assertAddsMessages(
            msg(ConstructorPositionChecker.MSG_PROPERTIES, (6, 4, 7), def2.targets[0]),
        ):
            self.checker.visit_functiondef(def1)

    def test_method_before_constructor(self):
        def1, def2 = \
            extract_node(
                '''
                class Foo:
                    def baz(self): #@
                        print()

                    def __init__(self): #@
                        print()
                ''',
            )
        with self.assertAddsMessages(
            msg(ConstructorPositionChecker.MSG_METHODS, (3, 4, 11), def1),
        ):
            self.checker.visit_functiondef(def2)

if __name__ == '__main__':
    main()
