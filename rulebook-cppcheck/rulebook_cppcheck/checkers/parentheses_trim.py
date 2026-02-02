from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class ParenthesesTrimChecker(RulebookTokenChecker):
    ID: str = 'parentheses-trim'
    MSG_FIRST: str = 'parentheses.trim.first'
    MSG_LAST: str = 'parentheses.trim.last'

    OPENING: set[str] = {'(', '[', '{', '<'}
    CLOSING: set[str] = {')', ']', '}', '>'}

    @override
    def process_token(self, token: Token) -> None:
        if token.str in self.OPENING:
            next_token: Token | None = token.next
            if not next_token or next_token.linenr <= token.linenr + 1:
                return
            if self._has_content_between(token, next_token):
                return
            self.report_error(token, _Messages.get(self.MSG_FIRST, token.str), token.linenr + 1)

        if token.str not in self.CLOSING:
            return
        prev_token: Token | None = token.previous
        if not prev_token or token.linenr <= prev_token.linenr + 1:
            return
        if self._has_content_between(prev_token, token):
            return
        self.report_error(token, _Messages.get(self.MSG_LAST, token.str), token.linenr - 1)

    @staticmethod
    def _has_content_between(start: Token, end: Token) -> bool:
        with open(start.file, 'r', encoding='UTF-8') as f:
            lines: list[str] = f.readlines()
            for i in range(start.linenr, end.linenr - 1):
                if lines[i].strip():
                    return True
        return False
