from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.source_word_meaning_checker import SourceWordMeaningChecker

from .testing import msg

class TestSourceWordMeaningChecker(CheckerTestCase):
    CHECKER_CLASS = SourceWordMeaningChecker

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
            msg(SourceWordMeaningChecker.MSG_ALL, (2, 0, 22), def1, 'Manager'),
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
            msg(SourceWordMeaningChecker.MSG_UTIL, (2, 0, 19), def1, 'Spaceships'),
        ):
            self.checker.visit_classdef(def1)

if __name__ == '__main__':
    main()
