from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class ParenthesesTrimChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#parentheses-trim"""
    ID: str = 'parentheses-trim'
    _MSG_FIRST: str = 'parentheses.trim.first'
    _MSG_LAST: str = 'parentheses.trim.last'

    _OPENING_PARENTHESES: set[str] = {'(', '[', '{', '<'}
    _CLOSING_PARENTHESES: set[str] = {')', ']', '}', '>'}

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        for token in [t for t in tokens if t.file]:
            # prepare file to for _has_content_between
            with open(token.file, 'r', encoding='UTF-8') as f:
                lines: list[str] = f.readlines()

            # find opening and closing parentheses
            if token.str in self._OPENING_PARENTHESES:
                next_token: Token | None = token.next
                if not next_token or next_token.linenr <= token.linenr + 1:
                    continue
                if self._has_content_between(lines, token, next_token):
                    continue
                self.report_error(token, _Messages.get(self._MSG_FIRST, token.str), token.linenr + 1)

            # checks for violation
            if token.str not in self._CLOSING_PARENTHESES:
                continue
            prev_token: Token | None = token.previous
            if not prev_token or token.linenr <= prev_token.linenr + 1:
                continue
            if self._has_content_between(lines, prev_token, token):
                continue
            self.report_error(token, _Messages.get(self._MSG_LAST, token.str), token.linenr - 1)

    @staticmethod
    def _has_content_between(lines2: list[str], start: Token, end: Token) -> bool:
        return any(lines2[i].strip() for i in range(start.linenr, end.linenr - 1))
