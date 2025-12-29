from unittest import main

from pylint.testutils import CheckerTestCase, _tokenize_str
from rulebook_pylint.checkers.unnecessary_blank_line_before_package import \
    UnnecessaryBlankLineBeforePackageChecker

from ..tests import assert_properties, msg


class TestUnnecessaryBlankLineBeforePackageChecker(CheckerTestCase):
    CHECKER_CLASS = UnnecessaryBlankLineBeforePackageChecker

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
        with self.assertAddsMessages(msg(UnnecessaryBlankLineBeforePackageChecker.MSG, 0)):
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
