from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.identifier_name import IdentifierNameChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestIdentifierNameChecker(CheckerTestCase):
    CHECKER_CLASS = IdentifierNameChecker

    @patch.object(IdentifierNameChecker, 'report_error')
    def test_valid_names(self, mock_report):
        self.checker.process_token(self._create_var_token('valid_variable'))
        self.checker.process_token(self._create_func_token('valid_function'))
        mock_report.assert_not_called()

    @patch.object(IdentifierNameChecker, 'report_error')
    def test_invalid_variable_name(self, mock_report):
        token = self._create_var_token('invalidVariable')
        self.checker.process_token(token)
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[0], token)
        self.assertEqual(args[1], _Messages.get(self.checker.MSG, 'invalid_variable'))

    @patch.object(IdentifierNameChecker, 'report_error')
    def test_invalid_function_name(self, mock_report):
        token = self._create_func_token('InvalidFunction')
        self.checker.process_token(token)
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG, 'invalid_function'))

    @staticmethod
    def _create_var_token(name):
        token = MagicMock()
        token.str = name
        token.variable = MagicMock()
        token.variable.nameToken = token
        return token

    @staticmethod
    def _create_func_token(name):
        token = MagicMock()
        token.str = name
        token.function = MagicMock()
        token.function.tokenDef = token
        return token

    @staticmethod
    def _create_configuration(tokens):
        configuration = MagicMock()
        configuration.tokenlist = tokens
        return configuration


if __name__ == '__main__':
    main()
