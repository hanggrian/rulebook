from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import Messages
from rulebook_cppcheck.nodes import next_sibling

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class IllegalCatchChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#illegal-catch"""
    ID: str = 'illegal-catch'
    _MSG: str = 'illegal.catch'

    _ILLEGAL_EXCEPTIONS: frozenset[str] = \
        frozenset([
            'exception',
            'std::exception',
        ])

    def process_tokens(self, tokens: list[Token]) -> None:
        for token in [t for t in tokens if t.str == 'catch']:
            next_token: Token | None = token.next
            if next_token is None or \
                next_token.str != '(':
                continue

            # checks for violation
            ellipses_token: Token | None = next_token.next
            if ellipses_token is not None and \
                ellipses_token.str == '...':
                self.report_error(token, Messages.get(self._MSG))
                continue
            if not next_sibling(next_token.next, lambda t: t.str in self._ILLEGAL_EXCEPTIONS):
                continue
            self.report_error(token, Messages.get(self._MSG))
