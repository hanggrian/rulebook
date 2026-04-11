from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import Messages
from rulebook_cppcheck.nodes import next_sibling

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class IllegalThrowChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#illegal-throw"""
    ID: str = 'illegal-throw'
    _MSG: str = 'illegal.throw'

    _BROAD_EXCEPTIONS: frozenset[str] = \
        frozenset([
            'exception',
            'std::exception',
        ])

    def process_tokens(self, tokens: list[Token]) -> None:
        # checks for violation
        for token in [t for t in tokens if t.str == 'throw']:
            target: Token | None = \
                next_sibling(
                    token.next,
                    lambda t: t.str in self._BROAD_EXCEPTIONS or t.str == ';',
                )
            if target is None or \
                target.str not in self._BROAD_EXCEPTIONS:
                continue
            self.report_error(target, Messages.get(self._MSG))
