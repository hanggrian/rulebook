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
            (
                // comment
                x
                // comment
            )
            ''',
    )
    def test_no_violations(self, mock_file, mock_report):
        opening = MagicMock(str='(', linenr=2, file='test.c')
        content = MagicMock(str='x', linenr=4, file='test.c')
        closing = MagicMock(str=')', linenr=6, file='test.c')
        opening.next = content
        content.previous = opening
        content.next = closing
        closing.previous = content
        config = MagicMock(tokenlist=[opening, content, closing])
        self.checker.run_check(config)
        mock_report.assert_not_called()
        mock_file.assert_called_with('test.c', 'r', encoding='UTF-8')

    @patch.object(ParenthesesTrimChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            {

                int

            }
            ''',
    )
    def test_trim_violations(self, mock_file, mock_report):
        opening = MagicMock(str='{', linenr=2, file='test.c')
        content = MagicMock(str='int', linenr=4, file='test.c')
        closing = MagicMock(str='}', linenr=6, file='test.c')
        opening.next = content
        content.previous = opening
        content.next = closing
        closing.previous = content
        config = MagicMock(tokenlist=[opening, content, closing])
        self.checker.run_check(config)
        self.assertEqual(mock_report.call_count, 2)
        mock_file.assert_called_with('test.c', 'r', encoding='UTF-8')
        self.assertEqual(
            mock_report.call_args_list[0][0][1],
            _Messages.get(self.checker.MSG_FIRST, '{'),
        )
        self.assertEqual(
            mock_report.call_args_list[1][0][1],
            _Messages.get(self.checker.MSG_LAST, '}'),
        )

    @patch.object(ParenthesesTrimChecker, 'report_error')
    @patch(
        'builtins.open',
        new_callable=mock_open,
        read_data= \
            '''
            {
                // comment
                int
                /* comment */
            }
            ''',
    )
    def test_comment_prevents_violation(self, mock_file, mock_report):
        opening = MagicMock(str='{', linenr=2, file='test.c')
        content = MagicMock(str='int', linenr=4, file='test.c')
        closing = MagicMock(str='}', linenr=6, file='test.c')
        opening.next = content
        content.previous = opening
        content.next = closing
        closing.previous = content
        config = MagicMock(tokenlist=[opening, content, closing])
        self.checker.run_check(config)
        mock_report.assert_not_called()
        mock_file.assert_called_with('test.c', 'r', encoding='UTF-8')


if __name__ == '__main__':
    main()
