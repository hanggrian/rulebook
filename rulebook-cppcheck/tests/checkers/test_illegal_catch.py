from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.illegal_catch import IllegalCatchChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestIllegalCatchChecker(CheckerTestCase):
    CHECKER_CLASS = IllegalCatchChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(IllegalCatchChecker, 'report_error')
    def test_ellipsis_violation(self, mock_report):
        token = MagicMock()
        token.str = 'catch'
        l_paren = MagicMock(str='(')
        ells = MagicMock(str='...')
        r_paren = MagicMock(str=')')
        token.next = l_paren
        l_paren.next = ells
        ells.next = r_paren
        self.checker.process_token(token)
        mock_report.assert_called_once()

    @patch.object(IllegalCatchChecker, 'report_error')
    def test_std_exception_violation(self, mock_report):
        token = MagicMock()
        token.str = 'catch'
        l_paren = MagicMock(str='(')
        exc = MagicMock(str='std::exception')
        r_paren = MagicMock(str=')')
        token.next = l_paren
        l_paren.next = exc
        exc.next = r_paren
        self.checker.process_token(token)
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))

    @patch.object(IllegalCatchChecker, 'report_error')
    def test_specific_exception_valid(self, mock_report):
        token = MagicMock()
        token.str = 'catch'
        l_paren = MagicMock(str='(')
        exc = MagicMock(str='std::runtime_error')
        r_paren = MagicMock(str=')')
        token.next = l_paren
        l_paren.next = exc
        exc.next = r_paren
        self.checker.process_token(token)
        mock_report.assert_not_called()


if __name__ == '__main__':
    main()
