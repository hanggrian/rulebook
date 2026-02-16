from __future__ import annotations

from astroid.nodes import Assign, Call, Name, AssignName
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages
from rulebook_pylint.nodes import _get_assignname

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class GenericNameChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#generic-name"""
    _MSG: str = 'generic.name'

    name: str = 'generic-name'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)

    def visit_assign(self, node: Assign) -> None:
        # only target TypeVar declaration
        if not isinstance(node.value, Call):
            return
        call: Call = node.value
        if not isinstance(call.func, Name) or call.func.name != 'TypeVar':
            return

        # checks for violation
        target: AssignName | None = _get_assignname(node)
        if not target:
            return
        name: str = target.name
        if len(name) == 1 and name[0].isupper():
            return
        self.add_message(self._MSG, node=target)


def register(linter: PyLinter) -> None:
    linter.register_checker(GenericNameChecker(linter))
