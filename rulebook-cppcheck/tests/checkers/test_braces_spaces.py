from textwrap import dedent
from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers import BracesSpacesChecker
from .checker_case import CheckerTestCase
from ..asserts import assert_properties


class TestBracesSpacesChecker(CheckerTestCase):
    CHECKER_CLASS = BracesSpacesChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(BracesSpacesChecker, 'report_error')
    def test_spaced_braces(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                struct Foo { int a = 0; }

                void bar() { int b = 0; }
                ''',
            ),
        )
        report_error.assert_not_called()

    @patch.object(BracesSpacesChecker, 'report_error')
    def test_unspaced_braces(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                struct Foo {int a = 0; }

                void bar() { int b = 0;}
                ''',
            ),
        )
        self.assertEqual(report_error.call_count, 2)
        calls = report_error.call_args_list
        self.assertEqual(calls[0][0][1], 'Add space inside single-line braces.')
        self.assertEqual(calls[0][0][2], 2)
        self.assertEqual(calls[1][0][1], 'Add space inside single-line braces.')
        self.assertEqual(calls[1][0][2], 4)

    @patch.object(BracesSpacesChecker, 'report_error')
    def test_skip_empty_braces(self, report_error):
        self.checker.check_file(
            self.mock_file(),
            dedent(
                '''
                struct Foo {}

                void bar() {}
                ''',
            ),
        )
        self.assertEqual(report_error.call_count, 0)


if __name__ == '__main__':
    main()
