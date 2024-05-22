from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase
from rulebook_pylint.string_single_quoting_checker import StringSingleQuotingChecker

from .testing import msg

class TestStringSingleQuotingChecker(CheckerTestCase):
    CHECKER_CLASS = StringSingleQuotingChecker

    def test_single_quote_string(self):
        def_all = \
            parse(
                '''
                foo = 'bar' #@
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(def_all)

    def test_double_empty_line(self):
        def_all = \
            parse(
                '''
                foo = "bar" #@
                ''',
            )
        with self.assertAddsMessages(msg(StringSingleQuotingChecker.MSG, (2, 6, 11))):
            self.checker.process_module(def_all)

    def test_skip_content_with_single_quote(self):
        def_all = \
            parse(
                '''
                foo = "'" #@
                ''',
            )
        with self.assertNoMessages():
            self.checker.process_module(def_all)

if __name__ == '__main__':
    main()
