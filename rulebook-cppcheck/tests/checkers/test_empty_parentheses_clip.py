from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.empty_parentheses_clip import EmptyParenthesesClipChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestEmptyParenthesesClipChecker(CheckerTestCase):
    CHECKER_CLASS = EmptyParenthesesClipChecker

    @patch.object(EmptyParenthesesClipChecker, 'report_error')
    def test_no_violations(self, mock_report):
        opening = MagicMock(str='{', linenr=1, column=10)
        closing = MagicMock(str='}', linenr=1, column=11)
        opening.next = closing
        config = MagicMock(tokenlist=[opening])
        self.checker.run_check(config)
        mock_report.assert_not_called()

    @patch.object(EmptyParenthesesClipChecker, 'report_error')
    def test_clip_violations(self, mock_report):
        opening = MagicMock(str='(', linenr=1, column=10)
        closing = MagicMock(str=')', linenr=1, column=12)
        opening.next = closing
        config = MagicMock(tokenlist=[opening])
        self.checker.run_check(config)
        mock_report.assert_called_once()
        self.assertEqual(mock_report.call_args[0][1], _Messages.get(self.checker.MSG, '()'))


if __name__ == '__main__':
    main()
