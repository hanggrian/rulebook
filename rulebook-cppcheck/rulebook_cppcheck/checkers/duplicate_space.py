from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class DuplicateSpaceChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#duplicate-space"""
    ID: str = 'duplicate-space'
    _MSG: str = 'duplicate.space'

    _IGNORE_TOKENS: tuple[str, ...] = (
        '=',
        ',',
        ';',
        '::',
        '(',
        ')',
        '{',
        '}',
        '[',
        ']',
        '<',
        '>',
        '*',
        '&',
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
        'struct',
        'class',
        'enum',
        'union',
        'typedef',
        'typename',
        'signed',
        'unsigned',
        'short',
        'long',
        'char',
        'int',
        'float',
        'double',
        'void',
        'bool',
        'auto',
    )

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        for token in tokens:
            # get last token to compare
            next_token: Token | None = token.next
            if next_token is None or token.linenr != next_token.linenr:
                continue

            # checks for violation
            if not self._is_duplicate_space(token, next_token):
                continue
            self.report_error(token, _Messages.get(self._MSG))

    def _is_duplicate_space(self, token: Token, next_token: Token) -> bool:
        gap: int = next_token.column - (token.column + len(token.str))
        if next_token.str.startswith('//') or next_token.str.startswith('/*'):
            return gap > 2
        if any(s in (token.str, next_token.str) for s in self._IGNORE_TOKENS):
            return False
        if next_token.isNumber:
            return False
        return gap > 1
