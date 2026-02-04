from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.duplicate_space import DuplicateSpaceChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestDuplicateSpaceChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateSpaceChecker

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_single_space_between_token(self, mock_report):
        tokens = [
            self._create_token('int', 1, 1),
            self._create_token('x', 1, 5),
            self._create_token('=', 1, 7),
            self._create_token('1', 1, 9),
            self._create_token('+', 1, 11),
            self._create_token('2', 1, 13),
            self._create_token(';', 1, 14),
        ]
        self._link_tokens(tokens)
        for token in tokens:
            self.checker.process_token(token)
        mock_report.assert_not_called()

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_multiple_spaces_between_token(self, mock_report):
        tokens = [
            self._create_token('int', 1, 1),
            self._create_token('x', 1, 5),
            self._create_token('y', 1, 8),
            self._create_token(';', 1, 9),
        ]
        self._link_tokens(tokens)
        for token in tokens:
            self.checker.process_token(token)

        self.assertEqual(mock_report.call_count, 1)
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))

    @staticmethod
    def _create_token(text, line, col):
        token = MagicMock()
        token.str = text
        token.linenr = line
        token.column = col
        return token

    @staticmethod
    def _link_tokens(tokens):
        for i in range(len(tokens) - 1):
            tokens[i].next = tokens[i + 1]
            tokens[i + 1].previous = tokens[i]


if __name__ == '__main__':
    main()
