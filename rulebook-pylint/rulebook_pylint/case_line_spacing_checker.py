from astroid import NodeNG, Match, MatchCase
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class CaseLineSpacingChecker(RulebookChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#case-line-spacing"""
    MSG_MISSING: str = 'case-line-spacing-missing'
    MSG_UNEXPECTED: str = 'case-line-spacing-unexpected'

    name: str = 'case-line-spacing'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_MISSING, MSG_UNEXPECTED)

    def visit_matchcase(self, node: MatchCase) -> None:
        # skip last branch
        if not isinstance(node.parent, Match):
            return
        match_cases: list[MatchCase] = node.parent.cases
        match_case_index = match_cases.index(node) + 1
        if len(match_cases) <= match_case_index:
            return
        match_case: MatchCase = match_cases[match_case_index]

        # checks for violation
        body: list[NodeNG] = node.body
        body_length: int = len(body)
        last_body: NodeNG = body[body_length - 1]
        if body_length > 1:
            if match_case.fromlineno == last_body.end_lineno + 2:
                return
            self.add_message(self.MSG_MISSING, node=node, line=last_body.end_lineno)
            return
        if match_case.fromlineno == last_body.end_lineno + 1:
            return
        self.add_message(self.MSG_UNEXPECTED, node=node, line=last_body.end_lineno)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(CaseLineSpacingChecker(linter))
