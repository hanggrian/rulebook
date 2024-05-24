from typing import TYPE_CHECKING

import regex
from astroid import ClassDef
from pylint.checkers import BaseChecker
from pylint.typing import MessageDefinitionTuple
from regex import Pattern

from .internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class ClassNameAcronymCapitalizationChecker(BaseChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#class-name-acronym-capitalization
    """
    MSG: str = 'class-name-acronym-capitalization'

    ABBREVIATION_REGEX: Pattern = regex.compile(r'[A-Z]{3,}')

    name: str = 'class-name-acronym-capitalization'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        if not bool(ClassNameAcronymCapitalizationChecker.ABBREVIATION_REGEX.search(node.name)):
            return None
        self.add_message(
            ClassNameAcronymCapitalizationChecker.MSG,
            node=node,
            args=ClassNameAcronymCapitalizationChecker._transform(node.name),
        )
        return None

    @staticmethod
    def _is_static_property_name(name: str) -> bool:
        return all(char.isupper() or char.isdigit() or char == '_' for char in name)

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
