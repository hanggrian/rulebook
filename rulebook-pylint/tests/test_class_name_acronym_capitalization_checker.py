from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.class_name_acronym_capitalization_checker \
    import ClassNameAcronymCapitalizationChecker

from .tests import msg


class TestClassNameAcronymCapitalizationChecker(CheckerTestCase):
    CHECKER_CLASS = ClassNameAcronymCapitalizationChecker

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
            msg(ClassNameAcronymCapitalizationChecker.MSG, (2, 0, 16), node1, 'MySqlClass'),
        ):
            self.checker.visit_classdef(node1)


if __name__ == '__main__':
    main()
