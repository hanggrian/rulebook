from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.string_literal_quotation_checker import StringLiteralQuotationChecker

from .tests import assert_properties, msg


class TestStringLiteralQuotationChecker(CheckerTestCase):
    CHECKER_CLASS = StringLiteralQuotationChecker

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

    def test_double_quote_string(self):
        tokens = \
            _tokenize_str(
                '''
                foo = "bar"
                ''',
            )
        with self.assertAddsMessages(msg(StringLiteralQuotationChecker.MSG_SINGLE, (2, 22, 27))):
            self.checker.process_tokens(tokens)

    def test_skip_content_with_quote_characters(self):
        tokens = \
            _tokenize_str(
                '''
                foo = "'"
                bar = '"'
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_flag_content_with_backslashed_quotes(self):
        tokens = \
            _tokenize_str(
                '''
                foo = "Hello \"World\""
                bar = 'Hello \'World\''
                ''',
            )
        with self.assertAddsMessages(
            msg(StringLiteralQuotationChecker.MSG_SINGLE, (2, 22, 30)),
            msg(StringLiteralQuotationChecker.MSG_SINGLE, (2, 35, 37)),
        ):
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
