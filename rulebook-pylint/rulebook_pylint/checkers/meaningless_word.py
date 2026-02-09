from re import Pattern, compile as re

from astroid.nodes import ClassDef
from pylint.typing import TYPE_CHECKING, Options

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class MeaninglessWordChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#meaningless-word"""
    MSG_ALL: str = 'meaningless.word.all'
    MSG_UTIL: str = 'meaningless.word.util'

    TITLE_CASE_REGEX: Pattern = \
        re(
            r'((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|' +
            r'([A-Z]+(?=([A-Z][a-z])|($)|([0-9]))))',
        )

    name: str = 'meaningless-word'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(MSG_ALL, MSG_UTIL)
    options: Options = (
        (
            'rulebook-meaningless-words',
            {
                'default': ('Util', 'Utility', 'Helper', 'Manager', 'Wrapper'),
                'type': 'csv',
                'metavar': '<comma-separated values>',
                'help': 'A set of banned names.',
            },
        ),
    )

    _words: list[str]

    def open(self) -> None:
        self._words = self.linter.config.rulebook_meaningless_words

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        words: list[str] = [match[0] for match in self.TITLE_CASE_REGEX.findall(node.name)]
        if not words or words[-1] not in self._words:
            return
        word: str = words[-1]
        if word in {'Util', 'Utility'}:
            self.add_message(
                self.MSG_UTIL,
                node=node,
                args=node.name[:node.name.index(word)] + 's',
                col_offset=node.col_offset + 6,
            )
            return
        self.add_message(self.MSG_ALL, node=node, args=word, col_offset=node.col_offset + 6)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(MeaninglessWordChecker(linter))
