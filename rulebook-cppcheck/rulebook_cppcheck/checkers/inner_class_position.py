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
    _MSG: str = 'inner.class.position'

    _TARGET_SCOPES: set[str] = {'Class', 'Struct'}
    _TARGET_TOKENS: set[str] = {'class', 'struct'}

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        for token in [
            t for t in tokens
            if t.scope and
               t.scope.type in self._TARGET_SCOPES and
               t is t.scope.bodyStart
        ]:
            current_class_scope: Scope = token.scope
            has_seen_inner_class: bool = False
            curr_token: Token | None = token.next
            continue_outer: int = False
            while curr_token is not None and curr_token is not current_class_scope.bodyEnd:
                if curr_token.str in self._TARGET_TOKENS and curr_token.next:
                    if curr_token.next.typeScope and \
                        curr_token.next.typeScope.nestedIn is current_class_scope:
                        has_seen_inner_class = True
                if has_seen_inner_class:
                    # checks for violation
                    if self._is_member(curr_token, current_class_scope):
                        self.report_error(curr_token, _Messages.get(self._MSG))
                        continue_outer = True
                        break
                curr_token = curr_token.next
            if continue_outer:
                continue

    @staticmethod
    def _is_member(token: Token, scope: Scope) -> bool:
        if token.scope is not scope:
            return False
        if token.function and token is token.function.tokenDef:
            return True
        if token.variable and token is token.variable.nameToken:
            return True
        return False
