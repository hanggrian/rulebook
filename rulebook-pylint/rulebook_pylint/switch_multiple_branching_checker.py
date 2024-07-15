from typing import TYPE_CHECKING

from astroid import Match
from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class SwitchMultipleBranchingChecker(Checker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#switch-multiple-branching
    """
    MSG: str = 'switch-multiple-branching'

    name: str = 'switch-multiple-branching'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def visit_match(self, node: Match) -> None:
        # checks for violation
        if len(node.cases) > 1:
            return
        self.add_message(self.MSG, node=node)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(SwitchMultipleBranchingChecker(linter))
