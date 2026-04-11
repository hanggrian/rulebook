from re import Pattern, compile as re

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import Messages
from rulebook_cppcheck.nodes import prev_sibling

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


class AbbreviationAsWordChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#abbreviation-as-word"""
    ID: str = 'abbreviation-as-word'
    _MSG: str = 'abbreviation.as.word'

    _ABBREVIATION_REGEX: Pattern = re(r'[A-Z]{3,}(?=[A-Z][a-z]|$)')

    def get_scopeset(self) -> set[str]:
        return {'Class', 'Struct', 'Union', 'Enum'}

    def visit_scope(self, scope: Scope) -> None:
        # checks for violation
        if scope.className is None or \
            not self._ABBREVIATION_REGEX.findall(scope.className):
            return
        name_token: Token | None = \
            prev_sibling(scope.bodyStart, lambda t: t.str == scope.className)
        self.report_error(
            name_token if name_token is not None else scope.bodyStart,
            Messages.get(
                self._MSG,
                self._ABBREVIATION_REGEX.sub(
                    lambda m: m.group(0)[0] + m.group(0)[1:].lower(),
                    scope.className,
                ),
            ),
        )
