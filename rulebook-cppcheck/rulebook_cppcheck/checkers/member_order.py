from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Scope, Token, Function, Variable
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token, Function, Variable


class MemberOrderChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#member-order"""
    ID: str = 'member-order'
    MSG: str = 'member.order'
    ARG_MEMBER_ORDER: str = 'member-order'
    ARGS = [ARG_MEMBER_ORDER]

    def __init__(self):
        super().__init__()
        self._member_order: list[str] = [
            'property',
            'constructor',
            'function',
            'static',
        ]
        self._property_position: int = 0
        self._constructor_position: int = 1
        self._function_position: int = 2
        self._static_position: int = 3

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self._member_order = args[self.ARG_MEMBER_ORDER].split(',')
        self._property_position = self._member_order.index('property')
        self._constructor_position = self._member_order.index('constructor')
        self._function_position = self._member_order.index('function')
        self._static_position = self._member_order.index('static')

    def _get_member_info(self, token: Token, scope: Scope) -> tuple[int, str] | None:
        if token.scope is not scope:
            return None
        if token.function:
            func: Function = token.function
            if token is not func.tokenDef:
                return None
            if func.isStatic:
                return self._static_position, 'static member'
            if scope.className == func.name:
                return self._constructor_position, 'constructor'
            return self._function_position, 'function'
        if token.variable:
            var: Variable = token.variable
            if token is not var.nameToken:
                return None
            if var.isStatic:
                return self._static_position, 'static member'
            return self._property_position, 'property'
        return None

    @override
    def process_token(self, token: Token) -> None:
        if not token.scope or token.scope.type not in ('Class', 'Struct'):
            return
        if token is not token.scope.bodyStart:
            return

        # checks for violation
        prev_weight: int | None = None
        prev_name: str | None = None
        curr_token: Token | None = token.next
        while curr_token and curr_token is not token.scope.bodyEnd:
            info: tuple[int, str] | None = self._get_member_info(curr_token, token.scope)
            if info:
                curr_weight, curr_name = info
                if prev_weight is not None and curr_weight < prev_weight:
                    self.report_error(curr_token, _Messages.get(self.MSG, curr_name, prev_name))
                prev_weight = curr_weight
                prev_name = curr_name
            curr_token = curr_token.next
