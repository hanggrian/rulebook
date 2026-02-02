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
    MSG: str = 'illegal.catch'

    @override
    def process_token(self, token: Token) -> None:
        if token.str != 'catch':
            return
        next_token: Token | None = token.next
        if not next_token or next_token.str != '(':
            return
        ellipses_token: Token | None = next_token.next
        if ellipses_token and ellipses_token.str == '...':
            self.report_error(token, _Messages.get(self.MSG))
            return
        if _next_sibling(next_token.next, lambda t: t.str != ')').str \
            not in {'exception', 'std::exception'}:
            return
        self.report_error(token, _Messages.get(self.MSG))

