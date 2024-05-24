from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.class_name_blacklisting_checker import ClassNameBlacklistingChecker

from .tests import msg

class TestClassNameBlacklistingChecker(CheckerTestCase):
    CHECKER_CLASS = ClassNameBlacklistingChecker

    def test_meaningful_class_names(self):
        def1 = \
            extract_node(
                '''
                class Spaceship: #@
                    print()
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_classdef(def1)

    def test_meaningless_class_names(self):
        def1 = \
            extract_node(
                '''
                class SpaceshipManager: #@
                    print()
                ''',
            )
        with self.assertAddsMessages(
            msg(ClassNameBlacklistingChecker.MSG_ALL, (2, 0, 22), def1, 'Manager'),
        ):
            self.checker.visit_classdef(def1)

    def test_utility_class_found(self):
        def1 = \
            extract_node(
                '''
                class SpaceshipUtil: #@
                    print()
                ''',
            )
        with self.assertAddsMessages(
            msg(ClassNameBlacklistingChecker.MSG_UTIL, (2, 0, 19), def1, 'Spaceships'),
        ):
            self.checker.visit_classdef(def1)

if __name__ == '__main__':
    main()
