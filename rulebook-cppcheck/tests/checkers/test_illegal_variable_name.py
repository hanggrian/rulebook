from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.illegal_variable_name import IllegalVariableNameChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestIllegalVariableNameChecker(CheckerTestCase):
    CHECKER_CLASS = IllegalVariableNameChecker

    @patch.object(IllegalVariableNameChecker, 'report_error')
    def test_descriptive_names(self, mock_report):
        self.checker.process_token(self._create_var_token('age'))
        self.checker.process_token(self._create_var_token('name'))
        self.checker.process_token(self._create_var_token('height'))
        self.checker.process_token(self._create_var_token('weight'))
        mock_report.assert_not_called()

    @patch.object(IllegalVariableNameChecker, 'report_error')
    def test_prohibited_names(self, mock_report):
        self.checker.process_token(self._create_var_token('integer'))
        self.checker.process_token(self._create_var_token('string'))
        self.assertEqual(mock_report.call_count, 2)
        self.assertEqual(mock_report.call_args_list[0][0][1], _Messages.get(self.checker.MSG))
        self.assertEqual(mock_report.call_args_list[1][0][1], _Messages.get(self.checker.MSG))

    @staticmethod
    def _create_var_token(name):
        token = MagicMock(str=name, function=None)
        token.variable = MagicMock(nameToken=token)
        return token


if __name__ == '__main__':
    main()
