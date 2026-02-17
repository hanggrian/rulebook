from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.indent_style import IndentStyleChecker
from ..tests import CheckerTestCase, assert_properties


class TestIndentStyleChecker(CheckerTestCase):
    CHECKER_CLASS = IndentStyleChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(IndentStyleChecker, 'report_error')
    def test_valid_indentation(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                    int bar = 0;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_not_called()

    @patch.object(IndentStyleChecker, 'report_error')
    def test_invalid_indentation(self, report_error):
        tokens, _ = \
            self.dump_code(
                '''
                void foo() {
                 int bar = 0;
                }
                ''',
            )
        self.checker.process_tokens(tokens)
        report_error.assert_called_once_with(
            next(t for t in tokens if t.str == 'int'),
            "Indent with '4' spaces.",
        )


if __name__ == '__main__':
    main()
