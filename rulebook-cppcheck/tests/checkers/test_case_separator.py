from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.case_separator import CaseSeparatorChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestCaseSeparatorChecker(CheckerTestCase):
    CHECKER_CLASS = CaseSeparatorChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_single_line_branches_without_line_break(self, mock_report):
        self.checker.visit_scope(
            self._create_switch_scope([
                {'type': 'case', 'lines': [3, 3]},
                {'type': 'case', 'lines': [4, 4]},
                {'type': 'default', 'lines': [5, 5]},
            ]),
        )
        mock_report.assert_not_called()

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_multiline_branches_with_line_break(self, mock_report):
        self.checker.visit_scope(
            self._create_switch_scope([
                {'type': 'case', 'lines': [3, 4]},
                {'type': 'case', 'lines': [6, 8]},
                {'type': 'default', 'lines': [10, 11]},
            ]),
        )
        mock_report.assert_not_called()

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_single_line_branches_with_line_break(self, mock_report):
        self.checker.visit_scope(
            self._create_switch_scope([
                {'type': 'case', 'lines': [3, 3]},
                {'type': 'case', 'lines': [5, 5]},
                {'type': 'default', 'lines': [7, 7]},
            ]),
        )
        self.assertEqual(mock_report.call_count, 2)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][0].linenr, 3)
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG_UNEXPECTED))
        self.assertEqual(calls[1][0][0].linenr, 5)
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG_UNEXPECTED))

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_multiline_branches_without_line_break(self, mock_report):
        self.checker.visit_scope(
            self._create_switch_scope([
                {'type': 'case', 'lines': [3, 4]},
                {'type': 'case', 'lines': [5, 7]},
                {'type': 'default', 'lines': [8, 9]},
            ]),
        )
        self.assertEqual(mock_report.call_count, 2)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][0].linenr, 4)
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG_MISSING))
        self.assertEqual(calls[1][0][0].linenr, 7)
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG_MISSING))

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_branches_with_comment_are_always_multiline(self, mock_report):
        self.checker.visit_scope(
            self._create_switch_scope([
                {'type': 'case', 'lines': [3, 5]},
                {'type': 'case', 'lines': [6, 8]},
                {'type': 'case', 'lines': [9, 11]},
                {'type': 'default', 'lines': [12, 13]},
            ]),
        )
        self.assertEqual(mock_report.call_count, 3)
        calls = mock_report.call_args_list
        self.assertEqual(calls[0][0][0].linenr, 5)
        self.assertEqual(calls[0][0][1], _Messages.get(self.checker.MSG_MISSING))
        self.assertEqual(calls[1][0][0].linenr, 8)
        self.assertEqual(calls[1][0][1], _Messages.get(self.checker.MSG_MISSING))
        self.assertEqual(calls[2][0][0].linenr, 11)
        self.assertEqual(calls[2][0][1], _Messages.get(self.checker.MSG_MISSING))

    @staticmethod
    def _create_switch_scope(group_data):
        scope = MagicMock()
        body_start = MagicMock()
        body_start.str = '{'
        body_start.linenr = 1
        body_end = MagicMock()
        body_end.str = '}'
        body_end.linenr = 20
        scope.bodyStart = body_start
        scope.bodyEnd = body_end
        tokens = [body_start]
        for data in group_data:
            start_token = MagicMock()
            start_token.str = data['type']
            start_token.linenr = data['lines'][0]
            end_token = MagicMock()
            end_token.str = ';'
            end_token.linenr = data['lines'][-1]
            start_token.next = end_token
            tokens.extend([start_token, end_token])
        tokens.append(body_end)
        for i in range(len(tokens) - 1):
            tokens[i].next = tokens[i + 1]
        tokens[-1].next = None
        return scope


if __name__ == '__main__':
    main()
