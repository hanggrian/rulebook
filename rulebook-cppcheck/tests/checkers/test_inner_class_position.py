from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.inner_class_position import InnerClassPositionChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestInnerClassPositionChecker(CheckerTestCase):
    CHECKER_CLASS = InnerClassPositionChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_inner_classes_at_the_bottom(self, mock_report):
        outer = self._scope()
        body_start = self._body_start(outer)
        outer.bodyEnd = self._token(outer, '}')
        self._chain([
            body_start,
            self._member_variable(outer, 'bar'),
            self._member_function(outer, 'baz'),
            *self._inner_class(outer, 'Inner'),
            *self._inner_class(outer, 'Another'),
            outer.bodyEnd,
        ])

        self.checker.process_token(body_start)
        mock_report.assert_not_called()

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_inner_classes_before_members(self, mock_report):
        outer = self._scope()
        body_start = self._body_start(outer)
        outer.bodyEnd = self._token(outer, '}')
        var_bar = self._member_variable(outer, 'bar')
        self._chain([
            body_start,
            *self._inner_class(outer, 'Inner'),
            var_bar,
            *self._inner_class(outer, 'Another'),
            self._member_function(outer, 'baz'),
            outer.bodyEnd,
        ])

        self.checker.process_token(body_start)
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertIs(args[0], var_bar)
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_skip_anonymous_inner_scope(self, mock_report):
        outer = self._scope()
        body_start = self._body_start(outer)
        outer.bodyEnd = self._token(outer, '}')
        kw_anon = self._token(outer, 'class')
        anon_next = self._token(outer, '{')
        kw_anon.next = anon_next
        self._chain(
            [body_start, kw_anon, anon_next, self._member_function(outer, 'bar'), outer.bodyEnd])

        self.checker.process_token(body_start)
        mock_report.assert_not_called()

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_target_struct_scope(self, mock_report):
        outer = self._scope('Struct')
        body_start = self._body_start(outer)
        outer.bodyEnd = self._token(outer, '}')
        var_member = self._member_variable(outer, 'member')
        self._chain([
            body_start,
            *self._inner_class(outer, 'Inner', 'struct'),
            var_member,
            outer.bodyEnd,
        ])

        self.checker.process_token(body_start)
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertIs(args[0], var_member)
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_nested_scope_tokens_are_skipped(self, mock_report):
        outer = self._scope()
        inner = MagicMock(nestedIn=outer)
        body_start = self._body_start(outer)
        outer.bodyEnd = self._token(outer, '}')
        func_bar = self._member_function(outer, 'bar')
        self._chain([
            body_start,
            *self._inner_class(outer, 'Inner'),
            self._token(inner, '{'),
            self._member_function(inner, 'nested'),
            self._token(inner, '}'),
            func_bar,
            outer.bodyEnd,
        ])

        self.checker.process_token(body_start)
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertIs(args[0], func_bar)
        self.assertEqual(args[1], _Messages.get(self.checker.MSG))

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_non_class_scope_is_ignored(self, mock_report):
        scope = self._scope('Function')
        self._body_start(scope)

        self.checker.process_token(scope.bodyStart)
        mock_report.assert_not_called()

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_non_body_start_token_is_ignored(self, mock_report):
        outer = self._scope()
        self._body_start(outer)

        self.checker.process_token(self._token(outer, 'int'))
        mock_report.assert_not_called()

    @staticmethod
    def _chain(tokens):
        for i in range(len(tokens) - 1):
            tokens[i].next = tokens[i + 1]
        tokens[-1].next = None

    @staticmethod
    def _scope(scope_type='Class'):
        scope = MagicMock()
        scope.type = scope_type
        return scope

    @staticmethod
    def _token(scope, s=';'):
        tok = MagicMock(str=s)
        tok.scope = scope
        return tok

    @staticmethod
    def _body_start(scope):
        tok = MagicMock(str='{')
        tok.scope = scope
        scope.bodyStart = tok
        return tok

    @staticmethod
    def _inner_class(scope, name='Inner', kw='class'):
        kw_tok = MagicMock(str=kw)
        kw_tok.scope = scope
        name_tok = MagicMock(str=name)
        name_tok.scope = scope
        name_tok.typeScope = MagicMock(nestedIn=scope)
        return kw_tok, name_tok

    @staticmethod
    def _member_function(scope, name='foo'):
        tok = MagicMock(str=name)
        tok.scope = scope
        tok.function = MagicMock(tokenDef=None)
        tok.function.tokenDef = tok
        return tok

    @staticmethod
    def _member_variable(scope, name='bar'):
        tok = MagicMock(str=name)
        tok.scope = scope
        tok.variable = MagicMock(nameToken=None)
        tok.variable.nameToken = tok
        return tok


if __name__ == '__main__':
    main()
