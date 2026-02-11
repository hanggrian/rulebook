from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.uppercase_l import UppercaseLChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestUppercaseLChecker(CheckerTestCase):
    CHECKER_CLASS = UppercaseLChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(UppercaseLChecker, 'report_error')
    def test_uppercase_literal_longs(self, mock_report):
        [self.checker.process_token(token) for token in self.dump_tokens('long l = 0L;')]
        mock_report.assert_not_called()

    @patch.object(UppercaseLChecker, 'report_error')
    def test_lowercase_literal_longs(self, mock_report):
        tokens = self.dump_tokens('long l = 0l;')
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == '0l'),
            _Messages.get(self.checker.MSG),
        )


if __name__ == '__main__':
    main()
