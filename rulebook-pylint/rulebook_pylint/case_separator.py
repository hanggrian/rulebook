from astroid import NodeNG, Match, MatchCase, Module
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookChecker
from rulebook_pylint.internals.files import get_fromlineno_after
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class CaseSeparatorChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/all/#case-separator"""
    MSG_MISSING: str = 'case-separator-missing'
    MSG_UNEXPECTED: str = 'case-separator-unexpected'

    name: str = 'case-separator'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_MISSING, MSG_UNEXPECTED)

    def visit_match(self, node: Match) -> None:
        # collect source code
        root: NodeNG = node
        while not isinstance(root, Module):
            root = root.parent
        root: Module
        lines: list[str]
        with root.stream() as stream:
            lines = [s.strip() for s in stream.readlines()]

        # collect cases
        match_cases: list[MatchCase] = node.cases

        for (i, match_case) in enumerate(match_cases):
            # targeting switch, skip first branch
            if i == 0:
                continue
            last_match_case: MatchCase = match_cases[i - 1]
            match_case_fromlineno = get_fromlineno_after(lines, match_case, last_match_case)
            last_match_case_fromlineno = \
                get_fromlineno_after(
                    lines,
                    last_match_case,
                    match_cases[i - 2] \
                        if i - 2 > -1 \
                        else node.subject,
                )
            last_body = last_match_case.body[len(last_match_case.body) - 1]

            # checks for violation
            if last_body.tolineno - 1 > last_match_case_fromlineno:
                if last_body.tolineno - 1 != match_case_fromlineno - 2:
                    self.add_message(self.MSG_MISSING, node=last_body)
                continue
            if last_body.tolineno - 1 == match_case_fromlineno - 1:
                continue
            self.add_message(self.MSG_UNEXPECTED, node=last_body)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(CaseSeparatorChecker(linter))
