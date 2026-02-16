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
    _MSG: str = 'illegal.throw'

    _BROAD_EXCEPTIONS: set[str] = {
        'exception',
        'std::exception',
    }

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        # checks for violation
        for token in [t for t in tokens if t.str == 'throw']:
            target: Token | None = \
                _next_sibling(
                    token.next,
                    lambda t: t.str in self._BROAD_EXCEPTIONS or t.str == ';',
                )
            if not target or target.str not in self._BROAD_EXCEPTIONS:
                continue
            self.report_error(target, _Messages.get(self._MSG))
