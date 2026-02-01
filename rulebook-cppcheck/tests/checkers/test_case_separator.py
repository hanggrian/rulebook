from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.case_separator import CaseSeparatorChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestCaseSeparatorChecker(CheckerTestCase):
    CHECKER_CLASS = CaseSeparatorChecker

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_valid_multiline_separation(self, mock_report):
        self.checker.visit_scope(
            self._create_switch_scope([
                {'type': 'case', 'lines': [2, 3]},
                {'type': 'case', 'lines': [5, 5]},
                {'type': 'default', 'lines': [7, 7]},
            ]),
        )
        mock_report.assert_not_called()

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_missing_separator(self, mock_report):
        self.checker.visit_scope(
            self._create_switch_scope([
                {'type': 'case', 'lines': [2, 3]},
                {'type': 'case', 'lines': [4, 4]},
            ]),
        )
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG_MISSING))

    @patch.object(CaseSeparatorChecker, 'report_error')
    def test_unexpected_separator(self, mock_report):
        self.checker.visit_scope(
            self._create_switch_scope([
                {'type': 'case', 'lines': [2, 2]},
                {'type': 'case', 'lines': [4, 4]},
            ]),
        )
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG_UNEXPECTED))

    @staticmethod
    def _create_switch_scope(group_data):
        scope = MagicMock()
        body_start = MagicMock()
        body_start.str = '{'
        body_end = MagicMock()
        body_end.str = '}'
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
