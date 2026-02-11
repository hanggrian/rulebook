from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.line_length import LineLengthChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestLineLengthChecker(CheckerTestCase):
    CHECKER_CLASS = LineLengthChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(LineLengthChecker, 'report_error')
    def test_valid_length(self, mock_report):
        [self.checker.process_token(token) for token in self.dump_tokens('int foo = 0;')]
        mock_report.assert_not_called()

    @patch.object(LineLengthChecker, 'report_error')
    def test_invalid_length(self, mock_report):
        tokens = self.dump_tokens(' ' * 90 + 'int foo = 0;')
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            tokens[0],
            _Messages.get(self.checker.MSG, 100),
        )


if __name__ == '__main__':
    main()
