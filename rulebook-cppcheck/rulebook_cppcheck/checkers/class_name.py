from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _prev_sibling

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token

# TODO find out why Enum is not caught in tests
class ClassNameChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#class-name"""
    ID: str = 'class-name'
    _MSG: str = 'class.name'

    @override
    def get_scopeset(self) -> set[str]:
        return {'Class', 'Struct', 'Union', 'Enum'}

    @override
    def visit_scope(self, scope: Scope) -> None:
        # checks for violation
        class_name: str | None = scope.className
        if not class_name or class_name[0].isupper() and '_' not in class_name:
            return
        name_token: Token | None = _prev_sibling(scope.bodyStart, lambda t: t.str == class_name)
        self.report_error(
            name_token if name_token else scope.bodyStart,
            _Messages.get(
                self._MSG,
                ''.join(p[0].upper() + p[1:] if p else '' for p in class_name.split('_')),
            ),
        )
