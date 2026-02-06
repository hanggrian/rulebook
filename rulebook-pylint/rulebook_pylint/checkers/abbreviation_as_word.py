from re import Pattern, compile as re

from astroid.nodes import ClassDef
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class AbbreviationAsWordChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#abbreviation-as-word"""
    MSG: str = 'abbreviation.as.word'

    ABBREVIATION_REGEX: Pattern = re(r'[A-Z]{3,}(?=[A-Z][a-z]|$)')

    name: str = 'abbreviation-as-word'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        class_name: str = node.name
        if not self.ABBREVIATION_REGEX.findall(class_name):
            return
        self.add_message(
            self.MSG,
            node=node,
            args= \
                AbbreviationAsWordChecker.ABBREVIATION_REGEX.sub(
                    lambda m: m.group(0)[0] + m.group(0)[1:].lower(),
                    class_name,
                ),
            col_offset=node.col_offset + 6,
        )


def register(linter: 'PyLinter') -> None:
    linter.register_checker(AbbreviationAsWordChecker(linter))
