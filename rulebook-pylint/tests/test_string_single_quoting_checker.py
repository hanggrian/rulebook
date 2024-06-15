from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.string_single_quoting_checker import StringSingleQuotingChecker

from .tests import msg


class TestStringSingleQuotingChecker(CheckerTestCase):
    CHECKER_CLASS = StringSingleQuotingChecker

    def test_single_quote_string(self):
        code = \
            '''
            foo = 'bar'
            '''
        with self.assertNoMessages():
            self.checker.process_tokens(_tokenize_str(code))

    def test_double_empty_line(self):
        code = \
            '''
            foo = "bar"
            '''
        with self.assertAddsMessages(msg(StringSingleQuotingChecker.MSG, (2, 18, 23))):
            self.checker.process_tokens(_tokenize_str(code))

    def test_skip_content_with_single_quote(self):
        code = \
            '''
            foo = "'"
            '''
        with self.assertNoMessages():
            self.checker.process_tokens(_tokenize_str(code))


if __name__ == '__main__':
    main()
