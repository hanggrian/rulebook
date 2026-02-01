from astroid.nodes import Match
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class UnnecessarySwitchChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#unnecessary-switch"""
    MSG: str = 'unnecessary.switch'

    name: str = 'unnecessary-switch'
    msgs: dict[str, MessageDefinitionTuple] = _Messages.of(MSG)

    def visit_match(self, node: Match) -> None:
        # checks for violation
        if len(node.cases) > 1:
            return
        self.add_message(self.MSG, node=node)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(UnnecessarySwitchChecker(linter))
