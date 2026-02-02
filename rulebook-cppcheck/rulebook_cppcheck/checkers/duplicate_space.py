from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class DuplicateSpaceChecker(RulebookTokenChecker):
    ID: str = 'duplicate-space'
    MSG: str = 'duplicate.space'

    @override
    def process_token(self, token: Token) -> None:
        next_token: Token | None = token.next
        if not next_token or token.linenr != next_token.linenr:
            return
        prev_token: Token | None = token.previous
        if not prev_token or prev_token.linenr != token.linenr:
            return
        if self._is_duplicate_space(token, next_token):
            self.report_error(token, _Messages.get(self.MSG))

    @staticmethod
    def _is_duplicate_space(token: Token, next_token: Token) -> bool:
        gap: int = next_token.column - (token.column + len(token.str))
        if next_token.str.startswith('//') or next_token.str.startswith('/*'):
            return gap > 2
        if any(s in (token.str, next_token.str) for s in (
                '=',
                ',',
                ';',
                '::',
                '(',
                ')',
                '{',
                '}',
                '<',
                '>',
                'if',
                'else',
                'while',
                'for',
                'switch',
                'catch',
                'return',
                'string',
                'constexpr',
                'inline',
                'static',
                'const',
        )):
            return False
        return gap > 1
