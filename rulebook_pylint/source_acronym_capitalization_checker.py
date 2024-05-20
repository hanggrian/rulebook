from typing import TYPE_CHECKING

import regex
from astroid import ClassDef
from pylint.checkers import BaseChecker
from pylint.typing import MessageDefinitionTuple
from regex import Pattern

from .internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class SourceAcronymCapitalizationChecker(BaseChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#source-acronym-capitalization
    """
    MSG: str = 'source.acronym.capitalization'

    ABBREVIATION_REGEX: Pattern = regex.compile(r'[A-Z]{3,}')

    name: str = 'source-acronym-capitalization'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        if not bool(SourceAcronymCapitalizationChecker.ABBREVIATION_REGEX.search(node.name)):
            return None
        self.add_message(
            SourceAcronymCapitalizationChecker.MSG,
            node=node,
            args=SourceAcronymCapitalizationChecker._transform(node.name),
        )

    @staticmethod
    def _is_static_property_name(name: str) -> bool:
        return all(char.isupper() or char.isdigit() or char == '_' for char in name)

    @staticmethod
    def _transform(name: str) -> str:
        def replace_match(match):
            group_value = match.group()
            if match.end() == len(name):
                return group_value[0] + group_value[1:].lower()
            else:
                return group_value[0] + group_value[1:-1].lower() + group_value[-1]

        return SourceAcronymCapitalizationChecker.ABBREVIATION_REGEX.sub(replace_match, name)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(SourceAcronymCapitalizationChecker(linter))
