from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.checkers.string_quotes import StringQuotesChecker

from ..tests import assert_properties, msg


class TestStringQuotesChecker(CheckerTestCase):
    CHECKER_CLASS = StringQuotesChecker

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
        with self.assertAddsMessages(msg(StringQuotesChecker.MSG_SINGLE, (2, 22, 27))):
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
            msg(StringQuotesChecker.MSG_SINGLE, (2, 22, 30)),
            msg(StringQuotesChecker.MSG_SINGLE, (2, 35, 37)),
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
            msg(StringQuotesChecker.MSG_SINGLE, (2, 22, 34)),
            msg(StringQuotesChecker.MSG_SINGLE, (3, 22, 34)),
        ):
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
