from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.generic_name import GenericNameChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestGenericNameChecker(CheckerTestCase):
    CHECKER_CLASS = GenericNameChecker

    @patch.object(GenericNameChecker, 'report_error')
    def test_valid_generic_name(self, mock_report):
        tokens = self._create_template_chain(['template', '<', 'typename', 'T', '>'])
        self.checker.process_token(tokens[0])
        mock_report.assert_not_called()

    @patch.object(GenericNameChecker, 'report_error')
    def test_invalid_generic_name(self, mock_report):
        tokens = self._create_template_chain(['template', '<', 'typename', 'XA', '>'])
        self.checker.process_token(tokens[0])
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[0].str, 'XA')
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))

    @patch.object(GenericNameChecker, 'report_error')
    def test_multiple_generics_ignored(self, mock_report):
        tokens = self._create_template_chain(
            ['template', '<', 'typename', 'T', ',', 'typename', 'U', '>'],
        )
        self.checker.process_token(tokens[0])
        mock_report.assert_not_called()

    @patch.object(GenericNameChecker, 'report_error')
    def test_variadic_template_ignored(self, mock_report):
        tokens = self._create_template_chain(['template', '<', 'typename', '...', 'Ts', '>'])
        self.checker.process_token(tokens[0])
        mock_report.assert_not_called()

    @patch.object(GenericNameChecker, 'report_error')
    def test_multiple_generics_ignored(self, mock_report):
        tokens = self._create_template_chain(
            ['template', '<', 'typename', 'T', ',', 'typename', 'U', '>'],
        )
        self.checker.process_token(tokens[0])
        mock_report.assert_not_called()

    @patch.object(GenericNameChecker, 'report_error')
    def test_variadic_template_ignored(self, mock_report):
        tokens = self._create_template_chain(['template', '<', 'typename', '...', 'Ts', '>'])
        self.checker.process_token(tokens[0])
        mock_report.assert_not_called()

    @staticmethod
    def _create_template_chain(token_strings):
        mocks = []
        for s in token_strings:
            t = MagicMock()
            t.str = s
            t.type = \
                'name' if s not in (
                    'template',
                    '<',
                    '>',
                    ',',
                    '...',
                    'typename',
                    'class',
                ) else 'keyword'
            t.next = None
            mocks.append(t)
        for i in range(len(mocks) - 1):
            mocks[i].next = mocks[i + 1]
        open_bracket = None
        for m in mocks:
            if m.str == '<':
                open_bracket = m
                break
        close_bracket = None
        for m in reversed(mocks):
            if m.str == '>':
                close_bracket = m
                break
        if open_bracket and close_bracket:
            open_bracket.link = close_bracket
        return mocks


if __name__ == '__main__':
    main()
