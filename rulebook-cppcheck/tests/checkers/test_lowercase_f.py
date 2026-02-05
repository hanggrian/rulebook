from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.lowercase_f import LowercaseFChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestLowercaseFChecker(CheckerTestCase):
    CHECKER_CLASS = LowercaseFChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(LowercaseFChecker, 'report_error')
    def test_lowercase_literal_floats(self, mock_report):
        token = self._create_float_token('0f')
        self.checker.process_token(token)
        mock_report.assert_not_called()

    @patch.object(LowercaseFChecker, 'report_error')
    def test_uppercase_literal_floats(self, mock_report):
        token = self._create_float_token('0F')
        self.checker.process_token(token)
        mock_report.assert_called_once_with(token, _Messages.get(self.checker.MSG))

    @staticmethod
    def _create_float_token(s):
        return MagicMock(str=s, isFloat=True)


if __name__ == '__main__':
    main()
