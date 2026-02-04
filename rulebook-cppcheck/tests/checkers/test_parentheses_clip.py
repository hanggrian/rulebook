from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.parentheses_clip import ParenthesesClipChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestParenthesesClipChecker(CheckerTestCase):
    CHECKER_CLASS = ParenthesesClipChecker

    @patch.object(ParenthesesClipChecker, 'report_error')
    def test_wrapped_parentheses(self, mock_report):
        opening = self._create_token('(', 2, 8)
        closing = self._create_token(')', 2, 9)
        opening.next = closing
        closing.previous = opening
        self.checker.process_token(opening)
        mock_report.assert_not_called()

    @patch.object(ParenthesesClipChecker, 'report_error')
    def test_unwrapped_parentheses(self, mock_report):
        opening1 = self._create_token('(', 1, 8)
        closing1 = self._create_token(')', 2, 1)
        opening1.next = closing1
        closing1.previous = opening1
        opening2 = self._create_token('(', 3, 8)
        closing2 = self._create_token(')', 5, 5)
        opening2.next = closing2
        closing2.previous = opening2
        self.checker.process_token(opening1)
        self.checker.process_token(opening2)
        self.assertEqual(mock_report.call_count, 2)
        msg = _Messages.get(self.checker.MSG, '()')
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][1], msg)
        self.assertEqual(calls[0][0][0].linenr, 1)
        self.assertEqual(calls[1][0][1], msg)
        self.assertEqual(calls[1][0][0].linenr, 3)

    @patch.object(ParenthesesClipChecker, 'report_error')
    def test_allow_parentheses_with_comment(self, mock_report):
        opening = self._create_token('(', 1, 8)
        comment = self._create_token('// Lorem ipsum.', 2, 5)
        closing = self._create_token(')', 3, 1)
        opening.next = comment
        comment.previous = opening
        comment.next = closing
        closing.previous = comment
        self.checker.process_token(opening)
        mock_report.assert_not_called()

    @staticmethod
    def _create_token(text, line, col):
        token = MagicMock()
        token.str = text
        token.linenr = line
        token.column = col
        return token


if __name__ == '__main__':
    main()
