from unittest import main
from unittest.mock import MagicMock, patch

from rulebook_cppcheck.checkers.member_order import MemberOrderChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestMemberOrderChecker(CheckerTestCase):
    CHECKER_CLASS = MemberOrderChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(MemberOrderChecker, 'report_error')
    def test_correct_member_layout(self, mock_report):
        self.checker.process_token(
            self._create_scope_chain([
                ('property', 'bar'),
                ('function', 'baz'),
            ]),
        )
        mock_report.assert_not_called()

    @patch.object(MemberOrderChecker, 'report_error')
    def test_property_after_function(self, mock_report):
        start_token = \
            self._create_scope_chain([
                ('function', 'baz'),
                ('property', 'bar'),
            ])
        self.checker.process_token(start_token)
        mock_report.assert_called_once_with(
            start_token.next.next,
            _Messages.get(self.checker.MSG, 'property', 'function'),
        )

    @patch.object(MemberOrderChecker, 'report_error')
    def test_nested_class_ignored(self, mock_report):
        outer = MagicMock(type='Class')
        inner = MagicMock(type='Class')
        t1 = self._create_member_token('function', 'f1', outer)
        t2 = self._create_member_token('property', 'p1', inner)
        t3 = self._create_member_token('function', 'f2', outer)
        t1.next = t2
        t2.next = t3
        t3.next = MagicMock(str='}')
        outer.bodyStart = MagicMock(next=t1, scope=outer)
        outer.bodyEnd = t3.next
        self.checker.process_token(outer.bodyStart)
        mock_report.assert_not_called()

    def _create_scope_chain(self, members):
        scope = MagicMock(type='Class')
        tokens = [self._create_member_token(m_t, m_n, scope) for m_t, m_n in members]
        for i in range(len(tokens) - 1):
            tokens[i].next = tokens[i + 1]
        tokens[-1].next = MagicMock(str='}')
        scope.bodyStart = MagicMock(next=tokens[0], scope=scope)
        scope.bodyEnd = tokens[-1].next
        return scope.bodyStart

    @staticmethod
    def _create_member_token(m_type, name, scope):
        token = MagicMock(scope=scope)
        if m_type == 'function':
            token.function = MagicMock(name=name, tokenDef=token, isStatic=False)
            token.variable = None
        else:
            token.variable = MagicMock(nameToken=token, isStatic=False)
            token.function = None
        return token


if __name__ == '__main__':
    main()
