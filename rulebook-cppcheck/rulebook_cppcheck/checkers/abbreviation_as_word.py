from re import Pattern, compile as re
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _prev_sibling

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


class AbbreviationAsWordChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#abbreviation-as-word"""
    ID: str = 'abbreviation-as-word'
    MSG: str = 'abbreviation.as.word'

    ABBREVIATION_REGEX: Pattern = re(r'[A-Z]{3,}(?=[A-Z][a-z]|$)')

    @override
    def get_scope_set(self) -> set[str]:
        return {'Class', 'Struct', 'Union', 'Enum'}

    @override
    def visit_scope(self, scope: Scope) -> None:
        class_name: str = scope.className
        if not self.ABBREVIATION_REGEX.findall(class_name):
            return
        name_token: Token | None = _prev_sibling(scope.bodyStart, lambda t: t.str == class_name)
        self.report_error(
            name_token if name_token else scope.bodyStart,
            _Messages.get(
                self.MSG,
                self.ABBREVIATION_REGEX.sub(
                    lambda m: m.group(0)[0] + m.group(0)[1:].lower(),
                    class_name,
                ),
            ),
        )
