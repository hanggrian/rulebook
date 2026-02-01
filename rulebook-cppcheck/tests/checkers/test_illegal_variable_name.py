from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.illegal_variable_name import IllegalVariableNameChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestIllegalVariableNameChecker(CheckerTestCase):
    CHECKER_CLASS = IllegalVariableNameChecker

    @patch.object(IllegalVariableNameChecker, 'report_error')
    def test_valid_names(self, mock_report):
        self.checker.process_token(self._create_var_token('myCount'))
        self.checker.process_token(self._create_func_token('fetchData'))
        mock_report.assert_not_called()

    @patch.object(IllegalVariableNameChecker, 'report_error')
    def test_illegal_variable_names(self, mock_report):
        for name in ('integer', 'string'):
            mock_report.reset_mock()
            token = self._create_var_token(name)
            self.checker.process_token(token)
            mock_report.assert_called_once_with(token, _Messages.get(self.checker.MSG))

    @patch.object(IllegalVariableNameChecker, 'report_error')
    def test_illegal_function_names(self, mock_report):
        name = 'strings'
        token = self._create_func_token(name)
        self.checker.process_token(token)
        mock_report.assert_called_once_with(token, _Messages.get(self.checker.MSG))

    @staticmethod
    def _create_var_token(name):
        token = MagicMock()
        token.str = name
        token.variable = MagicMock()
        token.variable.nameToken = token
        token.function = None
        return token

    @staticmethod
    def _create_func_token(name):
        token = MagicMock()
        token.str = name
        token.function = MagicMock()
        token.function.tokenDef = token
        token.variable = None
        return token


if __name__ == '__main__':
    main()
