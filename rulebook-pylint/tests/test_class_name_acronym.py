from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.class_name_acronym import ClassNameAcronymChecker

from .tests import assert_properties, msg


class TestClassNameAcronymChecker(CheckerTestCase):
    CHECKER_CLASS = ClassNameAcronymChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_class_names_with_lowercase_abbreviation(self):
        node1 = \
            extract_node(
                '''
                class MySqlClass:  #@
                    print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(node1)

    def test_class_names_with_uppercase_abbreviation(self):
        node1 = \
            extract_node(
                '''
                class MySQLClass:  #@
                    print()
                ''',
            )
        with self.assertAddsMessages(
            msg(ClassNameAcronymChecker.MSG, (2, 6, 16), node1, 'MySqlClass'),
        ):
            self.checker.visit_classdef(node1)


if __name__ == '__main__':
    main()
