import unittest

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.source_acronym_capitalization_checker import SourceAcronymCapitalizationChecker

from .testing import msg


# pylint: disable=missing-class-docstring
class TestSourceAcronymCapitalizationChecker(CheckerTestCase):
    CHECKER_CLASS = SourceAcronymCapitalizationChecker

    def test_class_names_with_lowercase_abbreviation(self):
        def1 = extract_node(
            '''
            class MySqlClass: #@
                print()
            ''',
        )
        with self.assertNoMessages():
            self.checker.visit_classdef(def1)

    def test_class_names_with_uppercase_abbreviation(self):
        def1 = extract_node(
            '''
            class MySQLClass: #@
                print()
            ''',
        )
        with self.assertAddsMessages(
            msg(SourceAcronymCapitalizationChecker.MSG, (2, 0, 16), def1, 'MySqlClass'),
        ):
            self.checker.visit_classdef(def1)


if __name__ == '__main__':
    unittest.main()
