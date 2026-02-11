from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.indent_style import IndentStyleChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestIndentStyleChecker(CheckerTestCase):
    CHECKER_CLASS = IndentStyleChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(IndentStyleChecker, 'report_error')
    def test_valid_indentation(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                void foo() {
                    int bar = 0;
                }
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(IndentStyleChecker, 'report_error')
    def test_invalid_indentation(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                void foo() {
                 int bar = 0;
                }
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == 'int'),
            _Messages.get(self.checker.MSG, 4),
        )


if __name__ == '__main__':
    main()
