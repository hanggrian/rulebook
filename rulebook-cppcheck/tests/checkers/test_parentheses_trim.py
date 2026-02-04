from unittest import main
from unittest.mock import MagicMock, patch, mock_open

from rulebook_cppcheck.checkers.parentheses_trim import ParenthesesTrimChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestParenthesesTrimChecker(CheckerTestCase):
    CHECKER_CLASS = ParenthesesTrimChecker

    @patch.object(ParenthesesTrimChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            void foo(
                int bar
            ) {
                printf(
                    "%d", bar
                );
            }
            ''',
    )
    def test_parentheses_without_newline_padding(self, _, mock_report):
        l_paren1 = self._create_token('(', 2)
        int_bar = self._create_token('int', 3)
        r_paren1 = self._create_token(')', 4)
        l_brace = self._create_token('{', 4)
        printf = self._create_token('printf', 5)
        l_paren2 = self._create_token('(', 5)
        fmt = self._create_token('"%d"', 6)
        r_paren2 = self._create_token(')', 7)
        l_paren1.next = int_bar
        int_bar.next = r_paren1
        r_paren1.next = l_brace
        l_brace.next = printf
        printf.next = l_paren2
        l_paren2.next = fmt
        fmt.next = r_paren2
        self.checker.process_token(l_paren1)
        self.checker.process_token(l_paren2)
        mock_report.assert_not_called()

    @patch.object(ParenthesesTrimChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            void foo(

                int bar

            ) {
                printf(

                    "%d", bar

                );
            }
            ''',
    )
    def test_parentheses_with_newline_padding(self, _, mock_report):
        l_paren1 = self._create_token('(', 2)
        int_bar = self._create_token('int', 4)
        r_paren1 = self._create_token(')', 6)
        l_paren2 = self._create_token('(', 7)
        fmt = self._create_token('"%d"', 9)
        r_paren2 = self._create_token(')', 11)
        l_paren1.next = int_bar
        int_bar.previous = l_paren1
        int_bar.next = r_paren1
        r_paren1.previous = int_bar
        l_paren2.next = fmt
        fmt.previous = l_paren2
        fmt.next = r_paren2
        r_paren2.previous = fmt
        self.checker.process_token(l_paren1)
        self.checker.process_token(r_paren1)
        self.checker.process_token(l_paren2)
        self.checker.process_token(r_paren2)
        self.assertEqual(mock_report.call_count, 4)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG_FIRST, '('))
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG_LAST, ')'))
        self.assertEqual(calls[2][0][1], _Messages.get(self.checker.MSG_FIRST, '('))
        self.assertEqual(calls[3][0][1], _Messages.get(self.checker.MSG_LAST, ')'))

    @patch.object(ParenthesesTrimChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            void foo(
                // Lorem
                int bar
                // ipsum.
            ) {
                printf(
                    // Lorem
                    "%d", bar
                    // ipsum.
                );
            }
            ''',
    )
    def test_comments_are_considered_content(self, _, mock_report):
        l_paren1 = self._create_token('(', 2)
        int_bar = self._create_token('int', 4)
        r_paren1 = self._create_token(')', 6)
        l_paren2 = self._create_token('(', 7)
        fmt = self._create_token('"%d"', 9)
        r_paren2 = self._create_token(')', 11)
        l_paren1.next = int_bar
        int_bar.previous = l_paren1
        int_bar.next = r_paren1
        r_paren1.previous = int_bar
        l_paren2.next = fmt
        fmt.previous = l_paren2
        fmt.next = r_paren2
        r_paren2.previous = fmt
        self.checker.process_token(l_paren1)
        self.checker.process_token(r_paren1)
        self.checker.process_token(l_paren2)
        self.checker.process_token(r_paren2)
        mock_report.assert_not_called()

    @staticmethod
    def _create_token(s, line):
        return MagicMock(str=s, linenr=line, file='test.cpp')


if __name__ == '__main__':
    main()
