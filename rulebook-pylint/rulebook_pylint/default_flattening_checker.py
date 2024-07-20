from typing import TYPE_CHECKING

from astroid import Match, MatchAs, MatchCase, Return, Raise, Break
from pylint.typing import MessageDefinitionTuple
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class DefaultFlatteningChecker(Checker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#default-flattening
    """
    MSG: str = 'default-flattening'

    name: str = 'default-flattening'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def visit_match(self, node: Match) -> None:
        # skip no default
        cases: list[MatchCase] = node.cases
        if len(cases) == 0:
            return
        default: MatchCase = cases[-1]
        if (not isinstance(default.pattern, MatchAs)) or default.pattern.name is not None:
            return

        # checks for violation
        for case in cases[:-1]:
            if not any(isinstance(node, (Return, Raise)) for node in case.body):
                return
        self.add_message(self.MSG, node=default)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(DefaultFlatteningChecker(linter))
