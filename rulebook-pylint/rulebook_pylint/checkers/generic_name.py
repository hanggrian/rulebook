from __future__ import annotations

from astroid.nodes import Assign, AssignName, Call, Name
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import Messages
from rulebook_pylint.nodes import get_assignname

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class GenericNameChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#generic-name"""
    _MSG: str = 'generic.name'

    name: str = 'generic-name'
    msgs: dict[str, tuple[str, str, str]] = Messages.of(_MSG)

    def visit_assign(self, node: Assign) -> None:
        # only target TypeVar declaration
        if not isinstance(node.value, Call) or \
            not isinstance(node.value.func, Name) or \
            node.value.func.name != 'TypeVar':
            return

        # checks for violation
        target: AssignName | None = get_assignname(node)
        if target is None:
            return
        if len(target.name) == 1 and \
            target.name[0].isupper():
            return
        self.add_message(self._MSG, node=target)


def register(linter: PyLinter) -> None:
    linter.register_checker(GenericNameChecker(linter))
