from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token, Scope
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token, Scope


class ParenthesesClipChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#parentheses-clip"""
    ID: str = 'parentheses-clip'
    MSG: str = 'parentheses.clip'

    TARGET_SCOPES: set[str] = {'Try', 'Catch', 'If', 'Else'}
    PARENTHESES: dict[str, str] = {
        '{': '}',
        '(': ')',
        '[': ']',
        '<': '>',
    }

    @override
    def process_token(self, token: Token) -> None:
        # filter out non-parentheses
        if token.str not in self.PARENTHESES:
            return

        # skip statements that can have multiple braces
        scope: Scope | None = token.scope
        if scope and scope.type in self.TARGET_SCOPES:
            if scope.type != 'If':
                return
            if scope.bodyEnd.next and scope.bodyEnd.next.str == 'else':
                return
            if scope.nestedIn and scope.nestedIn.type == 'Else':
                return

        # checks for violation
        closing_parenthesis: str = self.PARENTHESES[token.str]
        next_token: Token | None = token.next
        if not next_token or next_token.str != closing_parenthesis:
            return
        if token.linenr == next_token.linenr and \
            token.column + 1 == next_token.column:
            return
        self.report_error(token, _Messages.get(self.MSG, token.str + closing_parenthesis))
