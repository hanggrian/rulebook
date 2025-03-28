import regex
from astroid import ClassDef
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple, Options
from regex import Pattern
from rulebook_pylint.checkers import RulebookChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class IllegalClassFinalNameChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/all/#illegal-class-final-name"""
    MSG_ALL: str = 'illegal-class-final-name-all'
    MSG_UTIL: str = 'illegal-class-final-name-util'

    TITLE_CASE_REGEX: Pattern = \
        regex.compile(
            r'((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|' +
            r'([A-Z]+(?=([A-Z][a-z])|($)|([0-9]))))',
        )

    name: str = 'illegal-class-final-name'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_ALL, MSG_UTIL)
    options: Options = (
        (
            'rulebook-illegal-class-final-names',
            {
                'default': ('Util', 'Utility', 'Helper', 'Manager', 'Wrapper'),
                'type': 'csv',
                'metavar': '<comma-separated names>',
                'help': 'A set of banned words.',
            },
        ),
    )

    _illegal_class_final_names: list[str]

    def open(self) -> None:
        self._illegal_class_final_names = self.linter.config.rulebook_illegal_class_final_names

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        for match in self.TITLE_CASE_REGEX.findall(node.name):
            word: str = match[0]
            if word not in self._illegal_class_final_names:
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
    linter.register_checker(IllegalClassFinalNameChecker(linter))
