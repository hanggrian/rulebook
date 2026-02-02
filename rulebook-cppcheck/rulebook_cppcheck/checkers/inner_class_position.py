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
        if token is not token.scope.bodyStart:
            return
        current_class_scope: Scope = token.scope
        has_seen_inner_class: bool = False
        curr_token: Token | None = token.next
        while curr_token and curr_token is not current_class_scope.bodyEnd:
            if curr_token.str in {'class', 'struct'} and curr_token.next:
                if curr_token.next.typeScope and \
                    curr_token.next.typeScope.nestedIn is current_class_scope:
                    has_seen_inner_class = True
            if has_seen_inner_class:
                # checks for violation
                if self._is_member(curr_token, current_class_scope):
                    self.report_error(curr_token, _Messages.get(self.MSG))
                    return
            curr_token = curr_token.next

    @staticmethod
    def _is_member(token: Token, scope: Scope) -> bool:
        if token.scope is not scope:
            return False
        if token.function and token is token.function.tokenDef:
            return True
        if token.variable and token is token.variable.nameToken:
            return True
        return False
