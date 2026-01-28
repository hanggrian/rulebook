from unittest import TestCase, main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.unnecessary_switch import UnnecessarySwitchChecker
from rulebook_cppcheck.messages import _Messages


class TestUnnecessarySwitchRule(TestCase):
    checker = UnnecessarySwitchChecker()

    @patch.object(UnnecessarySwitchChecker, 'report_error')
    def test_multiple_switch_branches(self, mock):
        configuration = \
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
            )
        self.checker.run_check(configuration)
        mock.assert_not_called()

    @patch.object(UnnecessarySwitchChecker, 'report_error')
    def test_single_switch_branch(self, mock):
        configuration = \
            self._create_configuration(
                ['switch', '(', 'expression', ')', '{', 'case', 'foo', ':', 'break', ';', '}'],
            )
        self.checker.run_check(configuration)
        mock.assert_called_once()
        args, _ = mock.call_args
        self.assertEqual(args[0].str, 'switch')
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))

    @staticmethod
    def _create_configuration(code_tokens, scope_type='Switch'):
        tokens = [MagicMock(str=s) for s in code_tokens]
        for i in range(len(tokens)):
            tokens[i].next = tokens[i + 1] if i < len(tokens) - 1 else None
            tokens[i].previous = tokens[i - 1] if i > 0 else None
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
