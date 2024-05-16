import unittest

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from source_word_meaning_checker import SourceWordMeaningChecker
from testing import msg


# pylint: disable=missing-class-docstring
class TestSourceWordMeaningChecker(CheckerTestCase):
    CHECKER_CLASS = SourceWordMeaningChecker

    def test_meaningful_class_names(self):
        def1, def2, def3, def4, def5, def6 = extract_node(
            '''
            class Spaceship: #@
                my_variable = 1
            class Rocket: #@
                my_variable = 1
            class Navigation: #@
                my_variable = 1
            class Planet: #@
                my_variable = 1
            class Route: #@
                my_variable = 1
            class Logger: #@
                my_variable = 1
            ''',
        )
        with self.assertNoMessages():
            self.checker.visit_classdef(def1)
            self.checker.visit_classdef(def2)
            self.checker.visit_classdef(def3)
            self.checker.visit_classdef(def4)
            self.checker.visit_classdef(def5)
            self.checker.visit_classdef(def6)

    def test_meaningless_class_names(self):
        def1, def2, def3, def4, def5, def6 = extract_node(
            '''
            class BaseSpaceship: #@
                stub = 1
            class AbstractRocket: #@
                stub = 1
            class NavigationHelper: #@
                stub = 1
            class PlanetInfo: #@
                stub = 1
            class RouteData: #@
                stub = 1
            class LoggerWrapper: #@
                stub = 1
            ''',
        )
        with self.assertAddsMessages(msg(SourceWordMeaningChecker, def1, (2, 0), 'Base')):
            self.checker.visit_classdef(def1)
        with self.assertAddsMessages(msg(SourceWordMeaningChecker, def2, (4, 0), 'Abstract')):
            self.checker.visit_classdef(def2)
        with self.assertAddsMessages(msg(SourceWordMeaningChecker, def3, (6, 0), 'Helper')):
            self.checker.visit_classdef(def3)
        with self.assertAddsMessages(msg(SourceWordMeaningChecker, def4, (8, 0), 'Info')):
            self.checker.visit_classdef(def4)
        with self.assertAddsMessages(msg(SourceWordMeaningChecker, def5, (10, 0), 'Data')):
            self.checker.visit_classdef(def5)
        with self.assertAddsMessages(msg(SourceWordMeaningChecker, def6, (12, 0), 'Wrapper')):
            self.checker.visit_classdef(def6)

    def test_violating_both_ends(self):
        def1 = extract_node(
            '''
            class BaseSpaceshipManager: #@
                stub = 1
            ''',
        )
        with self.assertAddsMessages(
            msg(SourceWordMeaningChecker, def1, (2, 0), 'Base'),
            msg(SourceWordMeaningChecker, def1, (2, 0), 'Manager'),
        ):
            self.checker.visit_classdef(def1)


if __name__ == '__main__':
    unittest.main()
