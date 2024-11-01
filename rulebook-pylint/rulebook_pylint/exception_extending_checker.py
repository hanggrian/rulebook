from astroid import ClassDef, NodeNG, Name
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ExceptionExtendingChecker(RulebookChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#exception-extending"""
    MSG: str = 'exception-extending'

    name: str = 'exception-extending'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        base: NodeNG
        for base in node.bases:
            if not isinstance(base, Name):
                continue

            # checks for violation
            if 'BaseException' not in base.name:
                continue
            self.add_message(self.MSG, node=base)
            return


def register(linter: 'PyLinter') -> None:
    linter.register_checker(ExceptionExtendingChecker(linter))
