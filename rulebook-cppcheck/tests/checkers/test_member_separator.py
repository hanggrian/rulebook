from unittest import main
from unittest.mock import MagicMock, patch, call

from rulebook_cppcheck.checkers.member_separator import MemberSeparatorChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestMemberSeparatorChecker(CheckerTestCase):
    CHECKER_CLASS = MemberSeparatorChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(MemberSeparatorChecker, 'report_error')
    def test_single_line_members_with_separator(self, mock_report):
        self.checker.visit_scope(self._create_class_scope([
            {'name': 'bar', 'type': 'var', 'line': 2, 'end_line': 2},
            {'name': 'Foo', 'type': 'func', 'line': 4, 'end_line': 4},
            {'name': 'baz', 'type': 'func', 'line': 6, 'end_line': 6},
        ]))
        mock_report.assert_not_called()

    @patch.object(MemberSeparatorChecker, 'report_error')
    def test_single_line_members_without_separator(self, mock_report):
        scope = self._create_class_scope([
            {'name': 'bar', 'type': 'var', 'line': 2, 'end_line': 2},
            {'name': 'Foo', 'type': 'func', 'line': 3, 'end_line': 3},
            {'name': 'baz', 'type': 'func', 'line': 4, 'end_line': 4},
        ])
        self.checker.visit_scope(scope)
        self.assertEqual(mock_report.call_count, 2)
        mock_report.assert_has_calls([
            call(scope.bodyStart.next.next, _Messages.get(self.checker.MSG, 'property')),
            call(
                scope.bodyStart.next.next.next.next.next,
                _Messages.get(self.checker.MSG, 'constructor'),
            ),
        ])

    @patch.object(MemberSeparatorChecker, 'report_error')
    def test_multiline_members_without_separator(self, mock_report):
        scope = self._create_class_scope([
            {'name': 'bar', 'type': 'var', 'line': 2, 'end_line': 4},
            {'name': 'Foo', 'type': 'func', 'line': 5, 'end_line': 7},
            {'name': 'baz', 'type': 'func', 'line': 8, 'end_line': 10},
        ])
        self.checker.visit_scope(scope)
        self.assertEqual(mock_report.call_count, 2)

    @patch.object(MemberSeparatorChecker, 'report_error')
    def test_skip_fields_grouped_together(self, mock_report):
        self.checker.visit_scope(self._create_class_scope([
            {'name': 'bar', 'type': 'var', 'line': 2, 'end_line': 2},
            {'name': 'baz', 'type': 'var', 'line': 3, 'end_line': 3},
            {'name': 'qux', 'type': 'var', 'line': 4, 'end_line': 6},
        ]))
        mock_report.assert_not_called()

    @staticmethod
    def _create_class_scope(member_data):
        scope = MagicMock()
        scope.className = 'Foo'
        body_start = MagicMock(linenr=1, variable=None, function=None)
        body_end = MagicMock(linenr=100, variable=None, function=None)
        scope.bodyStart = body_start
        scope.bodyEnd = body_end
        tokens = [body_start]
        for data in member_data:
            start = MagicMock(str=data['name'], linenr=data['line'], scope=scope)
            end = \
                MagicMock(
                    str=';' if data['type'] == 'var' else '}',
                    linenr=data['end_line'],
                    scope=scope,
                    variable=None,
                    function=None,
                )
            if data['type'] == 'var':
                v = MagicMock()
                v.nameToken = start
                start.variable, start.function = v, None
                tokens.extend([start, end])
                continue
            f = MagicMock()
            f.tokenDef = start
            start.variable, start.function = None, f
            brace = \
                MagicMock(
                    str='{',
                    scope=scope,
                    variable=None,
                    function=None,
                    link=end,
                )
            tokens.extend([start, brace, end])
        tokens.append(body_end)
        for i in range(len(tokens) - 1):
            tokens[i].next = tokens[i + 1]
        tokens[-1].next = None
        return scope


if __name__ == '__main__':
    main()
