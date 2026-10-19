from unittest import main

# noinspection protected-member
from pylint.testutils import CheckerTestCase, _tokenize_str

from rulebook_pylint.checkers import NoLeadingBlankLineChecker
from testing.messages import msg
from ..asserts import assert_properties


# noinspection PyTypeChecker
class TestNoLeadingBlankLineChecker(CheckerTestCase):
    CHECKER_CLASS = NoLeadingBlankLineChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_trimmed_file(self):
        tokens = \
            _tokenize_str(
                '''import unittest
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)

    def test_padded_file(self):
        tokens = \
            _tokenize_str(
                '''
                import unittest
                ''',
            )
        with self.assertAddsMessages(msg(NoLeadingBlankLineChecker._MSG, 1)):
            self.checker.process_tokens(tokens)

    def test_skip_comment(self):
        tokens = \
            _tokenize_str(
                '''# Lorem ipsum.

                import unittest
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_tokens(tokens)


if __name__ == '__main__':
    main()
