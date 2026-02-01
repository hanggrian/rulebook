from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.member_separator import MemberSeparatorChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestMemberSeparatorChecker(CheckerTestCase):
    CHECKER_CLASS = MemberSeparatorChecker

    @patch.object(MemberSeparatorChecker, 'report_error')
    def test_valid_separation(self, mock_report):
        scope = self._create_class_scope([
            {'name': 'x', 'type': 'var', 'line': 2, 'end_line': 2},
            {'name': 'y', 'type': 'var', 'line': 3, 'end_line': 3},
            {'name': 'func', 'type': 'func', 'line': 5, 'end_line': 7}
        ])
        self.checker.visit_scope(scope)
        mock_report.assert_not_called()

    @patch.object(MemberSeparatorChecker, 'report_error')
    def test_invalid_separation(self, mock_report):
        self.checker.visit_scope(
            self._create_class_scope([
                {'name': 'MyClass', 'type': 'func', 'line': 2, 'end_line': 4},
                {'name': 'getVal', 'type': 'func', 'line': 5, 'end_line': 6},
            ]),
        )
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG, 'constructor'))

    @patch.object(MemberSeparatorChecker, 'report_error')
    def test_variable_to_function_requires_line(self, mock_report):
        self.checker.visit_scope(
            self._create_class_scope([
                {'name': 'data', 'type': 'var', 'line': 2, 'end_line': 2},
                {'name': 'process', 'type': 'func', 'line': 3, 'end_line': 4},
            ]),
        )
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG, 'property'))

    def _create_class_scope(self, member_data):
        scope = MagicMock()
        scope.className = 'MyClass'

        body_start = MagicMock()
        body_start.str = '{'
        body_start.linenr = 1
        body_start.variable = body_start.function = None

        body_end = MagicMock()
        body_end.str = '}'
        body_end.linenr = 100
        body_end.variable = body_end.function = None

        scope.bodyStart = body_start
        scope.bodyEnd = body_end

        tokens = [body_start]
        for data in member_data:
            start_t = MagicMock()
            start_t.str = data['name']
            start_t.linenr = data['line']
            start_t.scope = scope

            end_t = MagicMock()
            end_t.linenr = data['end_line']
            end_t.scope = scope
            end_t.variable = end_t.function = None

            if data['type'] == 'var':
                start_t.variable = MagicMock()
                start_t.function = None
                end_t.str = ';'
                tokens.extend([start_t, end_t])
            else:
                start_t.variable = None
                func = MagicMock()
                func.tokenDef = start_t
                start_t.function = func

                brace_open = MagicMock()
                brace_open.str = '{'
                brace_open.variable = brace_open.function = None
                brace_open.scope = scope

                end_t.str = '}'
                brace_open.link = end_t
                tokens.extend([start_t, brace_open, end_t])

        tokens.append(body_end)

        for i in range(len(tokens) - 1):
            tokens[i].next = tokens[i + 1]
        tokens[-1].next = None

        return scope


if __name__ == '__main__':
    main()
