from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _next_sibling

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class IllegalThrowChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#illegal-throw"""
    ID: str = 'illegal-throw'
    MSG: str = 'illegal.throw'

    BROAD_EXCEPTIONS: set[str] = {
        'exception',
        'std::exception',
    }

    @override
    def process_token(self, token: Token) -> None:
        # checks for violation
        if token.str != 'throw':
            return
        target: Token | None = \
            _next_sibling(
                token.next,
                lambda t: t.str in self.BROAD_EXCEPTIONS or t.str == ';',
            )
        if not target or target.str not in self.BROAD_EXCEPTIONS:
            return
        self.report_error(target, _Messages.get(self.MSG))
