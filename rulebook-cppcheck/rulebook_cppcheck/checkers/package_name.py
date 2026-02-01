from re import Pattern, sub, compile as re
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _prev_sibling

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


class PackageNameChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#package-name"""
    ID: str = 'package-name'
    MSG: str = 'package.name'

    SNAKE_CASE_REGEX: Pattern = re(r'(?<!^)(?=[A-Z][a-z])|(?<=[a-z])(?=[A-Z])')

    @override
    def get_scope_set(self) -> set[str]:
        return {'Namespace'}

    @override
    def visit_scope(self, scope: Scope) -> None:
        class_name: str = scope.className
        if not any(c.isupper() for c in class_name):
            return
        name_token: Token | None = _prev_sibling(scope.bodyStart, lambda t: t.str == class_name)
        self.report_error(
            name_token if name_token else scope.bodyStart,
            _Messages.get(
                self.MSG,
                sub(r'_+', '_', self.SNAKE_CASE_REGEX.sub('_', class_name).lower()),
            ),
        )
