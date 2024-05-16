import unittest

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from source_acronym_capitalization_checker import SourceAcronymCapitalizationChecker
from testing import msg


# pylint: disable=missing-class-docstring
class TestSourceAcronymCapitalizationChecker(CheckerTestCase):
    CHECKER_CLASS = SourceAcronymCapitalizationChecker

    def test_class_names_with_lowercase_abbreviation(self):
        def1 = extract_node(
            '''
            class MySqlClass: #@
                stub = 1
            ''',
        )
        with self.assertNoMessages():
            self.checker.visit_classdef(def1)

    def test_class_names_with_uppercase_abbreviation(self):
        def1 = extract_node(
            '''
            class MySQLClass: #@
                stub = 1
            ''',
        )
        with self.assertAddsMessages(
            msg(SourceAcronymCapitalizationChecker, def1, (2, 0), 'MySqlClass'),
        ):
            self.checker.visit_classdef(def1)


if __name__ == '__main__':
    unittest.main()
