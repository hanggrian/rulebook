from astroid.nodes import NodeNG, Match, MatchCase
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_pylint.files import _get_fromlineno_before, _has_comment_above
from rulebook_pylint.messages import _Messages
from rulebook_pylint.nodes import _is_multiline

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class CaseSeparatorChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#case-separator"""
    MSG_MISSING: str = 'case.separator.missing'
    MSG_UNEXPECTED: str = 'case.separator.unexpected'

    name: str = 'case-separator'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(MSG_MISSING, MSG_UNEXPECTED)

    def visit_match(self, node: Match) -> None:
        # collect cases
        match_cases: list[MatchCase] = node.cases
        if len(match_cases) == 0:
            return

        # checks for violation
        has_multiline = \
            any(
                _is_multiline(match_case) or
                _has_comment_above(self.lines, match_case) for
                match_case in match_cases
            )
        for (i, match_case) in enumerate(match_cases):
            if i == 0:
                continue
            last_match_case: MatchCase = match_cases[i - 1]
            match_case_fromlineno: int = \
                _get_fromlineno_before(self.lines, match_case, last_match_case)
            last_body: NodeNG = last_match_case.body[-1]
            if has_multiline:
                if last_body.tolineno - 1 != match_case_fromlineno - 2:
                    self.add_message(
                        self.MSG_MISSING,
                        line=last_body.lineno,
                        end_lineno=last_body.end_lineno,
                        col_offset=last_body.col_offset,
                        end_col_offset=last_body.end_col_offset,
                    )
            elif last_body.tolineno - 1 != match_case_fromlineno - 1:
                self.add_message(
                    self.MSG_UNEXPECTED,
                    line=last_body.lineno,
                    end_lineno=last_body.end_lineno,
                    col_offset=last_body.col_offset,
                    end_col_offset=last_body.end_col_offset,
                )


def register(linter: 'PyLinter') -> None:
    linter.register_checker(CaseSeparatorChecker(linter))
