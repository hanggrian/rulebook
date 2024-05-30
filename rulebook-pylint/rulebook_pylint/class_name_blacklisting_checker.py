from typing import TYPE_CHECKING

import regex
from astroid import ClassDef
from pylint.typing import MessageDefinitionTuple, Options
from regex import Pattern
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class ClassNameBlacklistingChecker(Checker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#class-name-blacklisting
    """
    MSG_ALL: str = 'class-name-blacklisting-all'
    MSG_UTIL: str = 'class-name-blacklisting-util'

    TITLE_CASE_REGEX: Pattern = \
        regex.compile(
            r'((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|' +
            r'([A-Z]+(?=([A-Z][a-z])|($)|([0-9]))))',
        )

    name: str = 'class-name-blacklisting'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG_ALL, MSG_UTIL)
    options: Options = (
        (
            'rulebook-blacklist-class-names',
            {
                'default': ('Util', 'Utility', 'Helper', 'Manager', 'Wrapper'),
                'type': 'csv',
                'metavar': '<comma-separated names>',
                'help': 'A set of banned words.',
            },
        ),
    )

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        for match in self.TITLE_CASE_REGEX.findall(node.name):
            word = match[0]
            if word not in self.linter.config.rulebook_blacklist_class_names:
                continue

            if word in {'Util', 'Utility'}:
                self.add_message(
                    ClassNameBlacklistingChecker.MSG_UTIL,
                    node=node,
                    args=node.name[:node.name.index(word)] + 's',
                )
                return None
            self.add_message(ClassNameBlacklistingChecker.MSG_ALL, node=node, args=word)
        return None

def register(linter: 'PyLinter') -> None:
    linter.register_checker(ClassNameBlacklistingChecker(linter))
