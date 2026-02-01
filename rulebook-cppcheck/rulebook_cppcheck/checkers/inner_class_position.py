from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


class InnerClassPositionChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#inner-class-position"""
    ID: str = 'inner-class-position'
    MSG: str = 'inner.class.position'

    @override
    def process_token(self, token: Token) -> None:
        if not token.scope or token.scope.type not in ('Class', 'Struct'):
            return
        if token != token.scope.bodyStart:
            return
        current_class_scope: Scope = token.scope
        has_seen_inner_class: bool = False
        curr: Token = token.next
        while curr and curr != current_class_scope.bodyEnd:
            if curr.str in {'class', 'struct'} and curr.next:
                if curr.next.typeScope and curr.next.typeScope.nestedIn == current_class_scope:
                    has_seen_inner_class = True
            if has_seen_inner_class:
                # checks for violation
                if self._is_member(curr, current_class_scope):
                    self.report_error(curr, _Messages.get(self.MSG))
                    return
            curr = curr.next

    @staticmethod
    def _is_member(token: Token, scope: Scope) -> bool:
        if token.scope != scope:
            return False
        if token.function and token == token.function.tokenDef:
            return True
        if token.variable and token == token.variable.nameToken:
            return True
        return False
