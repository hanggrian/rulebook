import regex
from astroid import ClassDef
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from regex import Pattern
from rulebook_pylint.checkers import RulebookChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ClassNameAcronymChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#class-name-acronym"""
    MSG: str = 'class-name-acronym'

    ABBREVIATION_REGEX: Pattern = regex.compile(r'[A-Z]{3,}')

    name: str = 'class-name-acronym'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        if not bool(self.ABBREVIATION_REGEX.search(node.name)):
            return
        self.add_message(
            self.MSG,
            node=node,
            args=self._transform(node.name),
            col_offset=node.col_offset + 6,
        )

    @staticmethod
    def _transform(name: str) -> str:
        def replace_match(match):
            group_value: str = match.group()
            return group_value[0] + group_value[1:].lower() \
                if match.end() == len(name) \
                else group_value[0] + group_value[1:-1].lower() + group_value[-1]

        return ClassNameAcronymChecker.ABBREVIATION_REGEX.sub(replace_match, name)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(ClassNameAcronymChecker(linter))
