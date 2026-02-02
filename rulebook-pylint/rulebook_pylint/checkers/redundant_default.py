from astroid.nodes import Match, MatchAs, MatchCase
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages
from rulebook_pylint.nodes import _has_jump_statement

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class RedundantDefaultChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#redundant-default"""
    MSG: str = 'redundant.default'

    name: str = 'redundant-default'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(MSG)

    def visit_match(self, node: Match) -> None:
        # skip no default
        cases: list[MatchCase] = node.cases
        if not cases:
            return
        default: MatchCase = cases[-1]
        if not isinstance(default.pattern, MatchAs) or default.pattern.name:
            return

        # checks for violation
        if not all(_has_jump_statement(node) for node in cases[:-1]):
            return
        self.add_message(self.MSG, node=default)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(RedundantDefaultChecker(linter))
