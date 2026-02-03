from re import Pattern, compile as re
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _prev_sibling

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


class MeaninglessWordChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#meaningless-word"""
    ID: str = 'meaningless-word'
    MSG: str = 'meaningless.word'
    ARG_MEANINGLESS_WORDS: str = 'meaningless-words'
    ARGS = [ARG_MEANINGLESS_WORDS]

    TITLE_CASE_REGEX: Pattern = \
        re(
            r'((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|'
            r'([A-Z]+(?=([A-Z][a-z])|($)|([0-9]))))',
        )

    def __init__(self):
        super().__init__()
        self._illegal_class_name_suffixes: set[str] = \
            {'Util', 'Utility', 'Helper', 'Manager', 'Wrapper'}

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self._illegal_class_name_suffixes = \
            set(args[self.ARG_MEANINGLESS_WORDS].split(','))

    @override
    def get_scope_set(self) -> set[str]:
        return {'Class', 'Struct', 'Union', 'Enum'}

    @override
    def visit_scope(self, scope: Scope) -> None:
        # checks for violation
        words: list[str] = [
            match[0] for match in self.TITLE_CASE_REGEX.findall(scope.className)
            if match[0] in self._illegal_class_name_suffixes
        ]
        if not words:
            return
        name_token: Token | None = \
            _prev_sibling(scope.bodyStart, lambda t: t.str == scope.className)
        self.report_error(
            name_token if name_token else scope.bodyStart,
            _Messages.get(self.MSG, words[-1]),
        )
