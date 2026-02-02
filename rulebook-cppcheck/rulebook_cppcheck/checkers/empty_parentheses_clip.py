from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class EmptyParenthesesClipChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#empty-parentheses-clip"""
    ID: str = 'empty-parentheses-clip'
    MSG: str = 'empty.parentheses.clip'

    PARENTHESES: dict[str, str] = {
        '{': '}',
        '(': ')',
        '[': ']',
        '<': '>',
    }

    @override
    def process_token(self, token: Token) -> None:
        # checks for violation
        if token.str not in self.PARENTHESES:
            return
        closing_parenthesis: str = self.PARENTHESES[token.str]
        next_token: Token | None = token.next
        if not next_token or next_token.str != closing_parenthesis:
            return
        if token.linenr == next_token.linenr and \
            token.column + len(token.str) == next_token.column:
            return
        self.report_error(token, _Messages.get(self.MSG, token.str + closing_parenthesis))
