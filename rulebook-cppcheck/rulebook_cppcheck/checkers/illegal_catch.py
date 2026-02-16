from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _next_sibling

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class IllegalCatchChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#illegal-catch"""
    ID: str = 'illegal-catch'
    _MSG: str = 'illegal.catch'

    _ILLEGAL_EXCEPTIONS: set[str] = {
        'exception',
        'exception&',
        'std::exception',
        'std::exception&',
    }

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        for token in [t for t in tokens if t.str == 'catch']:
            next_token: Token | None = token.next
            if not next_token or next_token.str != '(':
                continue
            ellipses_token: Token | None = next_token.next
            if ellipses_token and ellipses_token.str == '...':
                self.report_error(token, _Messages.get(self._MSG))
                continue
            if not _next_sibling(next_token.next, lambda t: t.str in self._ILLEGAL_EXCEPTIONS):
                continue
            self.report_error(token, _Messages.get(self._MSG))
