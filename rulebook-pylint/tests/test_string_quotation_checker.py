from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.string_quotation_checker import StringQuotationChecker

from .tests import assert_properties, msg


class TestStringQuotationChecker(CheckerTestCase):
    CHECKER_CLASS = StringQuotationChecker

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
        with self.assertAddsMessages(msg(StringQuotationChecker.MSG_SINGLE, (2, 22, 27))):
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

    def test_skip_docstring(self):
        tokens = \
            _tokenize_str(
                '''
                """
                Lorem ipsum.
                """

                def foo():
                    """Lorem ipsum."""
                    print()
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
            msg(StringQuotationChecker.MSG_SINGLE, (2, 22, 30)),
            msg(StringQuotationChecker.MSG_SINGLE, (2, 35, 37)),
        ):
            self.checker.process_tokens(tokens)

    def test_flag_content_with_letter_prefix(self):
        tokens = \
            _tokenize_str(
                '''
                foo = f"aa bb {0}"
                bar = r"Hello {0}"
                ''',
            )
        with self.assertAddsMessages(
            msg(StringQuotationChecker.MSG_SINGLE, (2, 22, 34)),
            msg(StringQuotationChecker.MSG_SINGLE, (3, 22, 34)),
        ):
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
