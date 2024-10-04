from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.string_literal_quoting_checker import StringLiteralQuotingChecker

from .tests import assert_properties, msg


class TestStringLiteralQuotingChecker(CheckerTestCase):
    CHECKER_CLASS = StringLiteralQuotingChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_single_quote_string(self):
        tokens = \
            _tokenize_str(
                '''
                foo = 'bar'
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_double_empty_line(self):
        tokens = \
            _tokenize_str(
                '''
                foo = "bar"
                ''',
            )
        with self.assertAddsMessages(msg(StringLiteralQuotingChecker.MSG, (2, 22, 27))):
            self.checker.process_tokens(tokens)

    def test_skip_content_with_single_quote(self):
        tokens = \
            _tokenize_str(
                '''
                foo = "'"
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
