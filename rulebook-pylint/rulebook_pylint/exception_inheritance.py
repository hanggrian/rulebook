from astroid import ClassDef, Name
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ExceptionInheritanceChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/all/#exception-inheritance"""
    MSG: str = 'exception-inheritance'

    name: str = 'exception-inheritance'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        for base in node.bases:
            if not isinstance(base, Name):
                continue

            # checks for violation
            if 'BaseException' not in base.name:
                continue
            self.add_message(self.MSG, node=base)
            return


def register(linter: 'PyLinter') -> None:
    linter.register_checker(ExceptionInheritanceChecker(linter))
