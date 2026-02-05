from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.parameter_wrap import ParameterWrapChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestParameterWrapChecker(CheckerTestCase):
    CHECKER_CLASS = ParameterWrapChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_single_line_parameters(self, mock_report):
        l_paren = self._create_token('(', 1)
        p1 = self._create_token('a', 1)
        comma = self._create_token(',', 1)
        p2 = self._create_token('b', 1)
        r_paren = self._create_token(')', 1)
        l_paren.next = p1
        p1.next = comma
        comma.next = p2
        p2.next = r_paren
        self.checker.process_token(l_paren)
        mock_report.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_multiline_parameters_each_with_newline(self, mock_report):
        l_paren = self._create_token('(', 1)
        p1 = self._create_token('a', 2)
        comma = self._create_token(',', 2)
        p2 = self._create_token('b', 3)
        r_paren = self._create_token(')', 4)
        l_paren.next = p1
        p1.next = comma
        comma.next = p2
        p2.next = r_paren
        self.checker.process_token(l_paren)
        mock_report.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_multiline_parameters_each_without_newline(self, mock_report):
        l_paren = self._create_token('(', 1)
        p1 = self._create_token('a', 2)
        comma = self._create_token(',', 2)
        p2 = self._create_token('b', 2)
        r_paren = self._create_token(')', 3)
        l_paren.next = p1
        p1.next = comma
        comma.next = p2
        p2.next = r_paren
        self.checker.process_token(l_paren)
        mock_report.assert_called_once_with(p2, _Messages.get(self.checker.MSG))

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_multiline_parameters_each_hugging_parenthesis(self, mock_report):
        l_paren = self._create_token('(', 1)
        p1 = self._create_token('a', 1)
        comma = self._create_token(',', 1)
        p2 = self._create_token('b', 2)
        r_paren = self._create_token(')', 2)
        l_paren.next = p1
        p1.next = comma
        comma.next = p2
        p2.next = r_paren
        self.checker.process_token(l_paren)
        mock_report.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_aware_of_chained_single_line_calls(self, mock_report):
        l_paren = self._create_token('(', 3)
        p1 = self._create_token("'Hello'", 3)
        comma1 = self._create_token(',', 3)
        p2 = self._create_token('1', 3)
        comma2 = self._create_token(',', 3)
        p3 = self._create_token('2', 3)
        r_paren = self._create_token(')', 3)
        l_paren.next = p1
        p1.next = comma1
        comma1.next = p2
        p2.next = comma2
        comma2.next = p3
        p3.next = r_paren
        self.checker.process_token(l_paren)
        mock_report.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_allow_comments_between_parameters(self, mock_report):
        l_paren = self._create_token('(', 1)
        p1 = self._create_token('a', 2)
        comma1 = self._create_token(',', 2)
        p2 = self._create_token('b', 4)
        comma2 = self._create_token(',', 4)
        p3 = self._create_token('c', 6)
        r_paren = self._create_token(')', 12)
        l_paren.next = p1
        p1.next = comma1
        comma1.next = p2
        p2.next = comma2
        comma2.next = p3
        p3.next = r_paren
        self.checker.process_token(l_paren)
        mock_report.assert_not_called()

    @patch.object(ParameterWrapChecker, 'report_error')
    def test_allow_sam(self, mock_report):
        l_paren = self._create_token('(', 2)
        lambda_start = self._create_token('(', 2)
        lambda_end = self._create_token(')', 2)
        arrow = self._create_token('->', 2)
        brace_start = self._create_token('{', 2)
        brace_end = self._create_token('}', 5)
        r_paren = self._create_token(')', 5)
        l_paren.next = lambda_start
        lambda_start.next = lambda_end
        lambda_end.next = arrow
        arrow.next = brace_start
        brace_start.next = brace_end
        brace_end.next = r_paren
        self.checker.process_token(l_paren)
        mock_report.assert_not_called()

    @staticmethod
    def _create_token(s, line):
        return MagicMock(str=s, linenr=line)


if __name__ == '__main__':
    main()
