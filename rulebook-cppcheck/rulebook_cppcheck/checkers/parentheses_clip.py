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
    _MSG: str = 'parentheses.clip'

    _TARGET_SCOPES: set[str] = {'Try', 'Catch', 'If', 'Else'}
    _PARENTHESES: dict[str, str] = {
        '{': '}',
        '(': ')',
        '[': ']',
        '<': '>',
    }

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        for token in [t for t in tokens if t.str in self._PARENTHESES]:
            # skip statements that can have multiple braces
            scope: Scope | None = token.scope
            if scope and scope.type in self._TARGET_SCOPES:
                if scope.type != 'If':
                    continue
                if scope.bodyEnd.next and scope.bodyEnd.next.str == 'else':
                    continue
                if scope.nestedIn and scope.nestedIn.type == 'Else':
                    continue

            # skip params without ids
            if token.str == '(' and token.isRemovedVoidParameter:
                continue

            # checks for violation
            closing_parenthesis: str = self._PARENTHESES[token.str]
            next_token: Token | None = token.next
            if not next_token or next_token.str != closing_parenthesis:
                continue
            if token.linenr == next_token.linenr and \
                token.column + 1 == next_token.column:
                continue
            self.report_error(token, _Messages.get(self._MSG, token.str + closing_parenthesis))
