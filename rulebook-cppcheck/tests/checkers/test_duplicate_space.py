from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.duplicate_space import DuplicateSpaceChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestDuplicateSpaceChecker(CheckerTestCase):
    CHECKER_CLASS = DuplicateSpaceChecker

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_spacing(self, mock_report):
        tokens = [
            self._create_token('int', 1, 1),
            self._create_token('x', 1, 5),
            self._create_token(';', 1, 6),
            self._create_token('int', 2, 1),
            self._create_token('y', 2, 6),
        ]
        self._link_tokens(tokens)
        config = MagicMock()
        config.tokenlist = tokens
        self.checker.run_check(config)
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))

    @patch.object(DuplicateSpaceChecker, 'report_error')
    def test_comments(self, mock_report):
        tokens = [
            self._create_token(';', 1, 1),
            self._create_token('//', 1, 4),
            self._create_token(';', 2, 1),
            self._create_token('/*', 2, 4),
            self._create_token(';', 3, 1),
            self._create_token('//', 3, 5),
        ]
        self._link_tokens(tokens)
        config = MagicMock()
        config.tokenlist = tokens
        self.checker.run_check(config)
        mock_report.assert_called_once()

    @staticmethod
    def _create_token(text, line, col):
        token = MagicMock()
        token.str = text
        token.linenr = line
        token.column = col
        token.next = None
        return token

    @staticmethod
    def _link_tokens(tokens):
        for i in range(len(tokens) - 1):
            tokens[i].next = tokens[i + 1]


if __name__ == '__main__':
    main()
