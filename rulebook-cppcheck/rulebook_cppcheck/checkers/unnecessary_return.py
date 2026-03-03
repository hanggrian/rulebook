from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Function, Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Function, Scope, Token


class UnnecessaryReturnChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#unnecessary-return"""
    ID: str = 'unnecessary-return'
    _MSG: str = 'unnecessary.return'

    @override
    def get_scopeset(self) -> set[str]:
        return {'Function'}

    @override
    def visit_scope(self, scope: Scope) -> None:
        function: Function | None = scope.function
        if function is None:
            return
        if function.tokenDef.previous.str != 'void':
            return
        last_token: Token | None = scope.bodyEnd.previous
        if not last_token or \
            last_token.str != ';' or \
            last_token.previous.str != 'return':
            return
        return_token: Token | None = last_token.previous
        if return_token is None:
            return
        prev_token: Token | None = return_token.previous
        if prev_token is None or prev_token.str not in {'{', ';'}:
            return
        self.report_error(return_token, _Messages.get(self._MSG))
