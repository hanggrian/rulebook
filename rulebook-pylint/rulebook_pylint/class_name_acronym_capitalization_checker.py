from typing import TYPE_CHECKING

import regex
from astroid import ClassDef
from pylint.typing import MessageDefinitionTuple
from regex import Pattern
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ClassNameAcronymCapitalizationChecker(Checker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#class-name-acronym-capitalization
    """
    MSG: str = 'class-name-acronym-capitalization'

    ABBREVIATION_REGEX: Pattern = regex.compile(r'[A-Z]{3,}')

    name: str = 'class-name-acronym-capitalization'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        if not bool(self.ABBREVIATION_REGEX.search(node.name)):
            return
        self.add_message(self.MSG, node=node, args=self._transform(node.name))

    @staticmethod
    def _transform(name: str) -> str:
        def replace_match(match):
            group_value = match.group()
            if match.end() == len(name):
                return group_value[0] + group_value[1:].lower()
            return group_value[0] + group_value[1:-1].lower() + group_value[-1]

        return ClassNameAcronymCapitalizationChecker.ABBREVIATION_REGEX.sub(replace_match, name)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(ClassNameAcronymCapitalizationChecker(linter))
