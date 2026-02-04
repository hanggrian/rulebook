from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.generic_name import GenericNameChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestGenericNameChecker(CheckerTestCase):
    CHECKER_CLASS = GenericNameChecker

    @patch.object(GenericNameChecker, 'report_error')
    def test_correct_generic_name_in_class(self, mock_report):
        self.checker.process_token(
            self._create_template_chain(
                ['template', '<', 'typename', 'T', '>', 'class', 'MyClass'],
            )[0],
        )
        self.checker.process_token(
            self._create_template_chain(
                ['template', '<', 'class', 'V', '>', 'class', 'MyInterface'],
            )[0],
        )
        mock_report.assert_not_called()

    @patch.object(GenericNameChecker, 'report_error')
    def test_incorrect_generic_name_in_class(self, mock_report):
        self.checker.process_token(
            self._create_template_chain(
                ['template', '<', 'typename', 'XA', '>', 'class', 'MyClass'],
            )[0],
        )
        self.checker.process_token(
            self._create_template_chain(
                ['template', '<', 'typename', 'Xa', '>', 'class', 'MyClass'],
            )[0],
        )
        self.assertEqual(mock_report.call_count, 2)
        msg = _Messages.get(self.checker.MSG)
        self.assertEqual(mock_report.call_args_list[0][0][1], msg)
        self.assertEqual(mock_report.call_args_list[0][0][0].str, 'XA')

    @patch.object(GenericNameChecker, 'report_error')
    def test_correct_generic_name_in_function(self, mock_report):
        self.checker.process_token(
            self._create_template_chain(
                ['template', '<', 'typename', 'E', '>', 'void', 'execute'],
            )[0],
        )
        mock_report.assert_not_called()

    @patch.object(GenericNameChecker, 'report_error')
    def test_incorrect_generic_name_in_function(self, mock_report):
        self.checker.process_token(
            self._create_template_chain(
                ['template', '<', 'typename', 'Xa', '>', 'void', 'execute'],
            )[0],
        )
        self.assertEqual(mock_report.call_count, 1)
        self.assertEqual(mock_report.call_args[0][0].str, 'Xa')

    @patch.object(GenericNameChecker, 'report_error')
    def test_skip_inner_generics(self, mock_report):
        self.checker.process_token(
            self._create_template_chain(
                ['template', '<', 'typename', 'T', '>', 'class', 'Foo'],
            )[0],
        )
        mock_report.assert_not_called()

    @staticmethod
    def _create_template_chain(token_strs):
        mocks = []
        for s in token_strs:
            t = MagicMock()
            t.str = s
            t.type = \
                'name' if s not in {
                    'template',
                    '<',
                    '>',
                    ',',
                    '...',
                    'typename',
                    'class',
                    'void',
                } else 'keyword'
            mocks.append(t)
        for i in range(len(mocks) - 1):
            mocks[i].next = mocks[i + 1]
        open_bracket = next((m for m in mocks if m.str == '<'), None)
        close_bracket = next((m for m in reversed(mocks) if m.str == '>'), None)
        if open_bracket and close_bracket:
            open_bracket.link = close_bracket
        return mocks


if __name__ == '__main__':
    main()
