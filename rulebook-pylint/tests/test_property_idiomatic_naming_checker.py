import unittest

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from property_idiomatic_naming_checker import PropertyIdiomaticNamingChecker
from testing import msg


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
            msg(PropertyIdiomaticNamingChecker, def1.targets[0], (2, 0)),
        ):
            self.checker.visit_assign(def1)
        with self.assertAddsMessages(
            msg(PropertyIdiomaticNamingChecker, def2.target, (3, 0)),
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

    def test_prohibited_de_structuring_parameter_names(self):
        def1 = extract_node(
            '''
            (integer, string) = (0, '') #@
            ''',
        )
        with self.assertAddsMessages(
            msg(PropertyIdiomaticNamingChecker, def1.targets[0].elts[0], (2, 1)),
            msg(PropertyIdiomaticNamingChecker, def1.targets[0].elts[1], (2, 10)),
        ):
            self.checker.visit_assign(def1)


if __name__ == '__main__':
    unittest.main()
