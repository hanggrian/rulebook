from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.uppercase_l import UppercaseLChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestUppercaseLChecker(CheckerTestCase):
    CHECKER_CLASS = UppercaseLChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(UppercaseLChecker, 'report_error')
    def test_uppercase_literal_longs(self, mock_report):
        token = self._create_number_token('0L')
        self.checker.process_token(token)
        mock_report.assert_not_called()

    @patch.object(UppercaseLChecker, 'report_error')
    def test_lowercase_literal_longs(self, mock_report):
        token = self._create_number_token('0l')
        self.checker.process_token(token)
        mock_report.assert_called_once_with(token, _Messages.get(self.checker.MSG))

    @staticmethod
    def _create_number_token(s):
        return MagicMock(str=s, isNumber=True)


if __name__ == '__main__':
    main()
