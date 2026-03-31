from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _next_sibling

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class ComplicatedAssignmentChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#complicated-assignment"""
    ID: str = 'complicated-assignment'
    _MSG: str = 'complicated.assignment'

    _SHORTHAND_OPERATIONS: frozenset[str] = frozenset(['+', '-', '*', '/', '%'])

    def process_tokens(self, tokens: list[Token]) -> None:
        for token in tokens:
            # skip shorthand operator
            if token.str != '=':
                continue

            # checks for violation
            lhs: Token | None = token.previous
            if lhs is None or not lhs.isName:
                continue
            match = \
                _next_sibling(
                    token.next,
                    lambda t: \
                        t.str == ';' or \
                        t.isName and \
                        t.str == lhs.str and \
                        t.next is not None and \
                        t.next.str in self._SHORTHAND_OPERATIONS,
                )
            if match is None or match.str == ';':
                continue
            self.report_error(token, _Messages.get(self._MSG, f'{match.next.str}='))
