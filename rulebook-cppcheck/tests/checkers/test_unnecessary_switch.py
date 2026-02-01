from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.unnecessary_switch import UnnecessarySwitchChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestUnnecessarySwitchRule(CheckerTestCase):
    CHECKER_CLASS = UnnecessarySwitchChecker

    @patch.object(UnnecessarySwitchChecker, 'report_error')
    def test_multiple_switch_branches(self, mock):
        self.checker.run_check(
            self._create_configuration(
                [
                    'switch',
                    '(',
                    'expression',
                    ')',
                    '{',
                    'case',
                    'foo',
                    ':',
                    'break',
                    ';',
                    'case',
                    'bar',
                    ':',
                    'break',
                    ';',
                    '}',
                ],
            ),
        )
        mock.assert_not_called()

    @patch.object(UnnecessarySwitchChecker, 'report_error')
    def test_single_switch_branch(self, mock):
        self.checker.run_check(
            self._create_configuration(
                ['switch', '(', 'expression', ')', '{', 'case', 'foo', ':', 'break', ';', '}'],
            ),
        )
        mock.assert_called_once()
        args, _ = mock.call_args
        self.assertEqual(args[0].str, 'switch')
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))

    @staticmethod
    def _create_configuration(code_tokens, scope_type='Switch'):
        tokens = [MagicMock(str=s) for s in code_tokens]
        for i, token in enumerate(tokens):
            token.next = tokens[i + 1] if i < len(tokens) - 1 else None
            token.previous = tokens[i - 1] if i > 0 else None
        scope = MagicMock()
        scope.type = scope_type
        try:
            brace_idx = code_tokens.index('{')
            scope.bodyStart = tokens[brace_idx]
            scope.bodyEnd = tokens[-1]
        except ValueError:
            pass
        configuration = MagicMock()
        configuration.scopes = [scope]
        configuration.tokenlist = tokens
        return configuration


if __name__ == '__main__':
    main()
