from astroid.nodes import ClassDef, FunctionDef, Name, NodeNG
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages
from rulebook_pylint.nodes import _has_decorator

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class AbstractClassDefinitionChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#abstract-class-definition"""
    MSG: str = 'abstract.class.definition'

    name: str = 'abstract-class-definition'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(MSG)

    def visit_classdef(self, node: ClassDef) -> None:
        # skip non-abstract class
        bases: list[NodeNG] = node.bases
        if not any(isinstance(n, Name) and n.name == 'ABC' for n in bases):
            return

        # checks for violation
        if len(bases) > 1 or \
            any(
                isinstance(n, FunctionDef) and
                _has_decorator(n, 'abstractmethod')
                for n in node.body
            ):
            return
        self.add_message(self.MSG, node=bases[0])


def register(linter: 'PyLinter') -> None:
    linter.register_checker(AbstractClassDefinitionChecker(linter))
