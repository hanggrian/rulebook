import unittest

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.property_idiomatic_naming_checker import PropertyIdiomaticNamingChecker
from .testing import msg


# pylint: disable=missing-class-docstring
class TestPropertyIdiomaticNamingChecker(CheckerTestCase):
    CHECKER_CLASS = PropertyIdiomaticNamingChecker

    def test_descriptive_property_names(self):
        def1, def2 = extract_node(
            '''
            age = 0 #@
            name: str = '' #@
            ''',
        )
        with self.assertNoMessages():
            self.checker.visit_assign(def1)
            self.checker.visit_assign(def2)

    def test_prohibited_property_names(self):
        def1, def2 = extract_node(
            '''
            integer = 0 #@
            string: str = '' #@
            ''',
        )
        with self.assertAddsMessages(
            msg(PropertyIdiomaticNamingChecker.MSG, (2, 0, 7), def1.targets[0]),
        ):
            self.checker.visit_assign(def1)
        with self.assertAddsMessages(
            msg(PropertyIdiomaticNamingChecker.MSG, (3, 0, 6), def2.target),
        ):
            self.checker.visit_assign(def2)

    def test_descriptive_de_structuring_parameter_names(self):
        def1 = extract_node(
            '''
            (age, name) = (0, '') #@
            ''',
        )
        with self.assertNoMessages():
            self.checker.visit_assign(def1)

    def test_attribute_assignment(self):
        def1, def2 = extract_node(
            '''
            class Foo:
                def __init__(self):
                    self.integer = 0 #@
                    self.string: str = '' #@
            ''',
        )
        with self.assertAddsMessages(
            msg(PropertyIdiomaticNamingChecker.MSG, (4, 8, 20), def1.targets[0]),
        ):
            self.checker.visit_assign(def1)
        with self.assertAddsMessages(
            msg(PropertyIdiomaticNamingChecker.MSG, (5, 8, 19), def2.target),
        ):
            self.checker.visit_assign(def2)

    def test_prohibited_de_structuring_parameter_names(self):
        def1 = extract_node(
            '''
            (integer, string) = (0, '') #@
            ''',
        )
        with self.assertAddsMessages(
            msg(PropertyIdiomaticNamingChecker.MSG, (2, 1, 8), def1.targets[0].elts[0]),
            msg(PropertyIdiomaticNamingChecker.MSG, (2, 10, 16), def1.targets[0].elts[1]),
        ):
            self.checker.visit_assign(def1)


if __name__ == '__main__':
    unittest.main()
