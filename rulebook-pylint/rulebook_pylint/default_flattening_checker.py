from astroid import Match, MatchAs, MatchCase
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookChecker
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import has_jump_statement

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class DefaultFlatteningChecker(RulebookChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#default-flattening"""
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
        if not all(has_jump_statement(node) for node in cases[:-1]):
            return
        self.add_message(self.MSG, node=default)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(DefaultFlatteningChecker(linter))
