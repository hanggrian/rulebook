from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.line_length import LineLengthChecker
from ..tests import assert_properties, CheckerTestCase


class TestLineLengthChecker(CheckerTestCase):
    CHECKER_CLASS = LineLengthChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(LineLengthChecker, 'report_error')
    def test_valid_length(self, report_error):
        tokens, _ = self.dump_code('int foo = 0;')
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(LineLengthChecker, 'report_error')
    def test_invalid_length(self, report_error):
        tokens, _ = self.dump_code(' ' * 90 + 'int foo = 0;')
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            tokens[0],
            "Code exceeds max line length of '100'.",
        )


if __name__ == '__main__':
    main()
