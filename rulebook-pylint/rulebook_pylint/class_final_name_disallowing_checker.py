from typing import TYPE_CHECKING

import regex
from astroid import ClassDef
from pylint.typing import MessageDefinitionTuple, Options
from regex import Pattern
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ClassFinalNameDisallowingChecker(Checker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#class-final-name-disallowing
    """
    MSG_ALL: str = 'class-final-name-disallowing-all'
    MSG_UTIL: str = 'class-final-name-disallowing-util'

    TITLE_CASE_REGEX: Pattern = \
        regex.compile(
            r'((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|' +
            r'([A-Z]+(?=([A-Z][a-z])|($)|([0-9]))))',
        )

    name: str = 'class-final-name-disallowing'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_ALL, MSG_UTIL)
    options: Options = \
        (
            (
                'rulebook-disallow-class-final-names',
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
            if word not in self.linter.config.rulebook_disallow_class_final_names:
                continue

            if word in {'Util', 'Utility'}:
                self.add_message(
                    self.MSG_UTIL,
                    node=node,
                    args=node.name[:node.name.index(word)] + 's',
                )
                return
            self.add_message(self.MSG_ALL, node=node, args=word)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(ClassFinalNameDisallowingChecker(linter))
