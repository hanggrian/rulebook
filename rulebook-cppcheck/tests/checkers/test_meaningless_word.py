from unittest import main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers.meaningless_word import MeaninglessWordChecker
from ..tests import CheckerTestCase, assert_properties


class TestMeaninglessWordChecker(CheckerTestCase):
    CHECKER_CLASS = MeaninglessWordChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(MeaninglessWordChecker, 'report_error')
    def test_meaningful_class_names(self, report_error):
        _, scopes = \
            self.dump_code(
                '''
                class Spaceship {}
                struct Rocket {}
                union Navigator {}
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()

    @patch.object(MeaninglessWordChecker, 'report_error')
    def test_meaningless_class_names(self, report_error):
        tokens, scopes = \
            self.dump_code(
                '''
                class SpaceshipManager {}
                struct RocketManager {}
                union NavigationManager {}
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_has_calls(
            [
                call(
                    next(t for t in tokens if t.str == 'SpaceshipManager'),
                    "Avoid meaningless word 'Manager'.",
                ),
                call(
                    next(t for t in tokens if t.str == 'RocketManager'),
                    "Avoid meaningless word 'Manager'.",
                ),
                call(
                    next(t for t in tokens if t.str == 'NavigationManager'),
                    "Avoid meaningless word 'Manager'.",
                ),
            ],
        )

    @patch.object(MeaninglessWordChecker, 'report_error')
    def test_allow_meaningless_prefix(self, report_error):
        _, scopes = \
            self.dump_code(
                '''
                class WrapperSpaceship {}
                struct WrapperRocket {}
                union WrapperNavigation {}
                ''',
            )
        [self.checker.visit_scope(scope) for scope in scopes]
        report_error.assert_not_called()


if __name__ == '__main__':
    main()
