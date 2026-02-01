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
    MSG: str = 'duplicate.space'

    @override
    def process_token(self, token: Token) -> None:
        # get last token to compare
        next_token: Token | None = token.next
        if not next_token or token.linenr != next_token.linenr:
            return

        # checks for violation
        if self._is_duplicate_space(token, next_token):
            self.report_error(token, _Messages.get(self.MSG))

    @staticmethod
    def _is_duplicate_space(token: Token, next_token: Token) -> bool:
        gap: int = next_token.column - (token.column + len(token.str))
        if next_token.str.startswith('//') or next_token.str.startswith('/*'):
            return gap > 2
        return gap > 1
