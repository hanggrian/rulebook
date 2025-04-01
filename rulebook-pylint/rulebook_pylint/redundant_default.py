from astroid import Match, MatchAs, MatchCase
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookChecker
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import has_jump_statement

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class RedundantDefault(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#redundant-default"""
    MSG: str = 'redundant-default'

    name: str = 'redundant-default'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def visit_match(self, node: Match) -> None:
        # skip no default
        cases: list[MatchCase] = node.cases
        if not cases:
            return
        default: MatchCase = cases[-1]
        if not isinstance(default.pattern, MatchAs) or default.pattern.name:
            return

        # checks for violation
        if not all(has_jump_statement(node) for node in cases[:-1]):
            return
        self.add_message(self.MSG, node=default)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(RedundantDefault(linter))
