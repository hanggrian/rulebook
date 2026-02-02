from re import Pattern, compile as re

from astroid.nodes import ClassDef
from pylint.typing import TYPE_CHECKING, Options

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class IllegalClassNameSuffixChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#illegal-class-name-suffix"""
    MSG_ALL: str = 'illegal.class.name.suffix.all'
    MSG_UTIL: str = 'illegal.class.name.suffix.util'

    TITLE_CASE_REGEX: Pattern = \
        re(
            r'((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|' +
            r'([A-Z]+(?=([A-Z][a-z])|($)|([0-9]))))',
        )

    name: str = 'illegal-class-name-suffix'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(MSG_ALL, MSG_UTIL)
    options: Options = (
        (
            'rulebook-illegal-class-name-suffixes',
            {
                'default': ('Util', 'Utility', 'Helper', 'Manager', 'Wrapper'),
                'type': 'csv',
                'metavar': '<comma-separated values>',
                'help': 'A set of banned words.',
            },
        ),
    )

    _illegal_class_name_suffixes: list[str]

    def open(self) -> None:
        self._illegal_class_name_suffixes = self.linter.config.rulebook_illegal_class_name_suffixes

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        words: list[str] = [
            match[0] for match in self.TITLE_CASE_REGEX.findall(node.name)
            if match[0] in self._illegal_class_name_suffixes
        ]
        if not words:
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
    linter.register_checker(IllegalClassNameSuffixChecker(linter))
