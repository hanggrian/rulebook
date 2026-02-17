from __future__ import annotations

from re import Pattern, compile as re

from astroid.nodes import ClassDef
from pylint.typing import Options, TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages
from rulebook_pylint.options import MEANINGLESS_WORDS_OPTION

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class MeaninglessWordChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#meaningless-word"""
    _MSG_ALL: str = 'meaningless.word.all'
    _MSG_UTIL: str = 'meaningless.word.util'

    _UTILITY_CLASS_NAMES: set[str] = {'Util', 'Utility'}
    _TITLE_CASE_REGEX: Pattern = \
        re(
            r'((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|' +
            r'([A-Z]+(?=([A-Z][a-z])|($)|([0-9]))))',
        )

    name: str = 'meaningless-word'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG_ALL, _MSG_UTIL)
    options: Options = (
        MEANINGLESS_WORDS_OPTION,
    )

    _words: list[str]

    def open(self) -> None:
        self._words = self.linter.config.rulebook_meaningless_words

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        words: list[str] = [match[0] for match in self._TITLE_CASE_REGEX.findall(node.name)]
        if not words or words[-1] not in self._words:
            return
        word: str = words[-1]
        if word in self._UTILITY_CLASS_NAMES:
            self.add_message(
                self._MSG_UTIL,
                node=node,
                args=node.name[:node.name.index(word)] + 's',
                col_offset=node.col_offset + 6,
            )
            return
        self.add_message(self._MSG_ALL, node=node, args=word, col_offset=node.col_offset + 6)


def register(linter: PyLinter) -> None:
    linter.register_checker(MeaninglessWordChecker(linter))
