from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.member_order import MemberOrderChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import CheckerTestCase


class TestMemberOrderChecker(CheckerTestCase):
    CHECKER_CLASS = MemberOrderChecker

    @patch.object(MemberOrderChecker, 'report_error')
    def test_valid_member_order(self, mock_report):
        self.checker.process_token(
            self._create_scope_chain([
                ('property', 'my_var'),
                ('function', 'my_func'),
            ]),
        )
        mock_report.assert_not_called()

    @patch.object(MemberOrderChecker, 'report_error')
    def test_invalid_member_order(self, mock_report):
        self.checker.process_token(
            self._create_scope_chain([
                ('function', 'my_func'),
                ('property', 'my_var'),
            ]),
        )
        mock_report.assert_called_once()
        args, _ = mock_report.call_args
        self.assertEqual(args[1], _Messages.get(self.checker.MSG, 'property', 'function'))

    @patch.object(MemberOrderChecker, 'report_error')
    def test_nested_class_ignored(self, mock_report):
        outer_scope = MagicMock()
        outer_scope.type = 'Class'
        outer_scope.className = 'Outer'
        inner_scope = MagicMock()
        inner_scope.type = 'Class'
        inner_scope.className = 'Inner'
        t1 = self._create_member_token('function', 'func1', outer_scope)
        t2 = self._create_member_token('property', 'inner_var', inner_scope)
        t3 = self._create_member_token('function', 'func2', outer_scope)
        t1.next = t2
        t2.next = t3
        t3.next = MagicMock(str='}')
        outer_scope.bodyStart = MagicMock(next=t1)
        outer_scope.bodyEnd = t3.next
        t1.scope = outer_scope
        t2.scope = inner_scope
        t3.scope = outer_scope
        self.checker.process_token(outer_scope.bodyStart)
        mock_report.assert_not_called()

    def _create_scope_chain(self, members):
        scope = MagicMock()
        scope.type = 'Class'
        scope.className = 'MyClass'
        tokens = []
        for m_type, m_name in members:
            tokens.append(self._create_member_token(m_type, m_name, scope))
        for i in range(len(tokens) - 1):
            tokens[i].next = tokens[i + 1]
        end_token = MagicMock(str='}')
        tokens[-1].next = end_token
        start_token = MagicMock(next=tokens[0])
        start_token.scope = scope
        scope.bodyStart = start_token
        scope.bodyEnd = end_token
        return start_token

    @staticmethod
    def _create_member_token(member_type, name, scope):
        token = MagicMock()
        token.scope = scope
        if member_type == 'function':
            token.function.name = name
            token.function.tokenDef = token
            token.function.isStatic = False
            token.variable = None
        else:
            token.variable.nameToken = token
            token.variable.isStatic = False
            token.function = None
        return token


if __name__ == '__main__':
    main()
