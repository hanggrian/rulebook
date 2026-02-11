from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _next_sibling

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


class CaseSeparatorChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#case-separator"""
    ID: str = 'case-separator'
    MSG_MISSING: str = 'case.separator.missing'
    MSG_UNEXPECTED: str = 'case.separator.unexpected'

    BRANCH_TOKENS: set[str] = {'case', 'default'}

    @override
    def get_scope_set(self) -> set[str]:
        return {'Switch'}

    @override
    def visit_scope(self, scope: Scope) -> None:
        # collect cases
        groups: list[list[Token]] = []
        curr_token: Token | None = scope.bodyStart.next
        while curr_token and curr_token is not scope.bodyEnd:
            if curr_token.str in self.BRANCH_TOKENS:
                temp: Token | None = \
                    _next_sibling(
                        curr_token.next,
                        lambda t: t is not scope.bodyEnd and t.str not in self.BRANCH_TOKENS,
                    )
                groups.append([curr_token, temp])
                curr_token = temp
                continue
            curr_token = curr_token.next

        # checks for violation
        if not groups:
            return
        has_multiline: bool = any(g[-1].linenr - g[0].linenr > 0 for g in groups)
        for i in range(1, len(groups)):
            prev_group: list[Token] = groups[i - 1]
            curr_group: list[Token] = groups[i]
            prev_end_line: int = prev_group[-1].linenr
            curr_start_line: int = curr_group[0].linenr
            line_diff: int = curr_start_line - prev_end_line
            if has_multiline:
                if line_diff < 2:
                    self.report_error(prev_group[-1], _Messages.get(self.MSG_MISSING))
            elif line_diff > 1:
                self.report_error(prev_group[-1], _Messages.get(self.MSG_UNEXPECTED))
