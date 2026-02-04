from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.meaningless_word import MeaninglessWordChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestMeaninglessWordChecker(CheckerTestCase):
    CHECKER_CLASS = MeaninglessWordChecker

    @patch.object(MeaninglessWordChecker, 'report_error')
    def test_meaningful_class_names(self, mock_report):
        for name in (
                'Spaceship',
                'Rocket',
                'Navigator',
                'Planet',
                'Route',
                'Logger',
        ):
            self.checker.visit_scope(self._create_scope_mock(name))
        mock_report.assert_not_called()

    @patch.object(MeaninglessWordChecker, 'report_error')
    def test_meaningless_class_names(self, mock_report):
        for name in (
                'SpaceshipManager',
                'RocketManager',
                'NavigationManager',
                'PlanetManager',
                'RouteManager',
                'LoggerManager',
        ):
            mock_report.reset_mock()
            scope = self._create_scope_mock(name)
            self.checker.visit_scope(scope)
            mock_report.assert_called_once_with(
                scope.bodyStart.previous,
                _Messages.get(self.checker.MSG, 'Manager'),
            )

    @staticmethod
    def _create_scope_mock(class_name):
        scope = MagicMock()
        scope.className = class_name
        body_start = MagicMock(str='{')
        name_token = MagicMock(str=class_name)
        body_start.previous = name_token
        scope.bodyStart = body_start
        return scope


if __name__ == '__main__':
    main()
