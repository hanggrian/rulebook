from re import Pattern, compile as re
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _prev_sibling
from rulebook_cppcheck.options import MEANINGLESS_WORDS_OPTION

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


class MeaninglessWordChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#meaningless-word"""
    ID: str = 'meaningless-word'
    _MSG: str = 'meaningless.word'
    ARGS: list[str] = [MEANINGLESS_WORDS_OPTION]

    _TITLE_CASE_REGEX: Pattern = \
        re(
            r'((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|'
            r'([A-Z]+(?=([A-Z][a-z])|($)|([0-9]))))',
        )

    def __init__(self) -> None:
        super().__init__()
        self._words: set[str] = \
            {'Util', 'Utility', 'Helper', 'Manager', 'Wrapper'}

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self._words = \
            set(args[MEANINGLESS_WORDS_OPTION].split(','))

    @override
    def get_scopeset(self) -> set[str]:
        return {'Class', 'Struct', 'Union', 'Enum'}

    @override
    def visit_scope(self, scope: Scope) -> None:
        # checks for violation
        class_name: str | None = scope.className
        if not class_name:
            return
        words: list[str] = [match[0] for match in self._TITLE_CASE_REGEX.findall(class_name)]
        if not words or words[-1] not in self._words:
            return
        name_token: Token | None = \
            _prev_sibling(scope.bodyStart, lambda t: t.str == class_name)
        self.report_error(
            name_token if name_token else scope.bodyStart,
            _Messages.get(self._MSG, words[-1]),
        )
