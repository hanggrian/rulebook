from astroid import Match
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class SwitchCaseBranchingChecker(Checker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#switch-case-branching
    """
    MSG: str = 'switch-case-branching'

    name: str = 'switch-case-branching'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def visit_match(self, node: Match) -> None:
        # checks for violation
        if len(node.cases) > 1:
            return
        self.add_message(self.MSG, node=node)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(SwitchCaseBranchingChecker(linter))
