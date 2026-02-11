from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.lowercase_f import LowercaseFChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestLowercaseFChecker(CheckerTestCase):
    CHECKER_CLASS = LowercaseFChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(LowercaseFChecker, 'report_error')
    def test_lowercase_literal_floats(self, mock_report):
        [self.checker.process_token(token) for token in self.dump_tokens('float f = 0.0f;')]
        mock_report.assert_not_called()

    @patch.object(LowercaseFChecker, 'report_error')
    def test_uppercase_literal_floats(self, mock_report):
        tokens = self.dump_tokens('float f = 0.0F;')
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == '0.0F'),
            _Messages.get(self.checker.MSG),
        )


if __name__ == '__main__':
    main()
