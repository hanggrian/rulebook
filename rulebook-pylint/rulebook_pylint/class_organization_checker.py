from typing import TYPE_CHECKING

from astroid import ClassDef, FunctionDef
from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import has_decorator

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ClassOrganizationChecker(Checker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#class-organization
    """
    MSG: str = 'class-organization'
    MSG_CONSTRUCTOR: str = 'constructor'
    MSG_FUNCTION: str = 'function'

    name: str = 'class-organization'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        last_method: FunctionDef | None = None
        method: FunctionDef
        for method in node.mymethods():
            # in Python, static members have are annotated
            if has_decorator(method, 'staticmethod'):
                continue

            # checks for violation
            if last_method is not None and \
                last_method.name != '__init__' and \
                method.name == '__init__':
                self.add_message(
                    self.MSG,
                    node=method,
                    args=(Messages.get(self.MSG_CONSTRUCTOR), Messages.get(self.MSG_FUNCTION)),
                )

            last_method = method


def register(linter: 'PyLinter') -> None:
    linter.register_checker(ClassOrganizationChecker(linter))
