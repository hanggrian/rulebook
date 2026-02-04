from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.redundant_else import RedundantElseChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestRedundantElseChecker(CheckerTestCase):
    CHECKER_CLASS = RedundantElseChecker

    @patch.object(RedundantElseChecker, 'report_error')
    def test_no_throw_or_return_in_if(self, mock_report):
        if_token = self._create_token('if')
        l_paren1 = self._create_token('(')
        r_paren1 = self._create_token(')')
        l_brace1 = self._create_token('{')
        baz1 = self._create_token('baz')
        r_brace1 = self._create_token('}')
        else_token1 = self._create_token('else')
        if_token2 = self._create_token('if')
        l_paren2 = self._create_token('(')
        r_paren2 = self._create_token(')')
        l_brace2 = self._create_token('{')
        baz2 = self._create_token('baz')
        r_brace2 = self._create_token('}')
        else_token2 = self._create_token('else')
        l_brace3 = self._create_token('{')
        baz3 = self._create_token('baz')
        r_brace3 = self._create_token('}')
        if_token.next = l_paren1
        l_paren1.link = r_paren1
        r_paren1.next = l_brace1
        l_brace1.link = r_brace1
        l_brace1.next = baz1
        baz1.next = r_brace1
        r_brace1.next = else_token1
        else_token1.next = if_token2
        if_token2.next = l_paren2
        l_paren2.link = r_paren2
        r_paren2.next = l_brace2
        l_brace2.link = r_brace2
        l_brace2.next = baz2
        baz2.next = r_brace2
        r_brace2.next = else_token2
        else_token2.next = l_brace3
        l_brace3.link = r_brace3
        l_brace3.next = baz3
        baz3.next = r_brace3
        self.checker.process_token(if_token)
        mock_report.assert_not_called()

    @patch.object(RedundantElseChecker, 'report_error')
    def test_lift_else_when_if_has_return(self, mock_report):
        if_token = self._create_token('if')
        l_paren1 = self._create_token('(')
        r_paren1 = self._create_token(')')
        l_brace1 = self._create_token('{')
        throw_token = self._create_token('throw')
        r_brace1 = self._create_token('}')
        else_token1 = self._create_token('else')
        if_token2 = self._create_token('if')
        l_paren2 = self._create_token('(')
        r_paren2 = self._create_token(')')
        l_brace2 = self._create_token('{')
        return_token = self._create_token('return')
        r_brace2 = self._create_token('}')
        else_token2 = self._create_token('else')
        l_brace3 = self._create_token('{')
        baz = self._create_token('baz')
        r_brace3 = self._create_token('}')
        if_token.next = l_paren1
        l_paren1.link = r_paren1
        r_paren1.next = l_brace1
        l_brace1.link = r_brace1
        l_brace1.next = throw_token
        throw_token.next = r_brace1
        r_brace1.next = else_token1
        else_token1.next = if_token2
        if_token2.next = l_paren2
        l_paren2.link = r_paren2
        r_paren2.next = l_brace2
        l_brace2.link = r_brace2
        l_brace2.next = return_token
        return_token.next = r_brace2
        r_brace2.next = else_token2
        else_token2.next = l_brace3
        l_brace3.link = r_brace3
        l_brace3.next = baz
        baz.next = r_brace3
        self.checker.process_token(if_token)
        self.assertEqual(mock_report.call_count, 2)
        self.assertEqual(mock_report.call_args_list[0][0][1], _Messages.get(self.checker.MSG))
        self.assertEqual(mock_report.call_args_list[1][0][1], _Messages.get(self.checker.MSG))

    @patch.object(RedundantElseChecker, 'report_error')
    def test_consider_if_else_without_blocks(self, mock_report):
        if_token = self._create_token('if')
        l_paren1 = self._create_token('(')
        r_paren1 = self._create_token(')')
        throw_token = self._create_token('throw')
        else_token1 = self._create_token('else')
        if_token2 = self._create_token('if')
        l_paren2 = self._create_token('(')
        r_paren2 = self._create_token(')')
        return_token = self._create_token('return')
        else_token2 = self._create_token('else')
        baz = self._create_token('baz')
        if_token.next = l_paren1
        l_paren1.link = r_paren1
        r_paren1.next = throw_token
        throw_token.next = else_token1
        else_token1.next = if_token2
        if_token2.next = l_paren2
        l_paren2.link = r_paren2
        r_paren2.next = return_token
        return_token.next = else_token2
        else_token2.next = baz
        self.checker.process_token(if_token)
        self.assertEqual(mock_report.call_count, 2)
        self.assertEqual(mock_report.call_args_list[0][0][1], _Messages.get(self.checker.MSG))
        self.assertEqual(mock_report.call_args_list[1][0][1], _Messages.get(self.checker.MSG))

    @staticmethod
    def _create_token(s):
        return MagicMock(str=s)


if __name__ == '__main__':
    main()
