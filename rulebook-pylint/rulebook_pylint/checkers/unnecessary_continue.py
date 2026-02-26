from __future__ import annotations

from astroid.nodes import Continue, For, NodeNG, While
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class UnnecessaryContinueChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#unnecessary-continue"""
    _MSG: str = 'unnecessary.continue'

    name: str = 'unnecessary-continue'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)

    def visit_for(self, node: For) -> None:
        self._process(node.body)

    def visit_while(self, node: While) -> None:
        self._process(node.body)

    def _process(self, body: list[NodeNG]) -> None:
        # checks for violation
        if not body:
            return
        continue2: NodeNG = body[-1]
        if not isinstance(continue2, Continue):
            return
        self.add_message(self._MSG, node=continue2)


def register(linter: PyLinter) -> None:
    linter.register_checker(UnnecessaryContinueChecker(linter))
