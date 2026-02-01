from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


class CaseSeparatorChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#case-separator"""
    ID: str = 'case-separator'
    MSG_MISSING: str = 'case.separator.missing'
    MSG_UNEXPECTED: str = 'case.separator.unexpected'

    @override
    def get_scope_set(self) -> set[str]:
        return {'Switch'}

    @override
    def visit_scope(self, scope: Scope) -> None:
        # collect cases
        groups: list[list[Token]] = []
        curr: Token | None = scope.bodyStart.next
        while curr and curr is not scope.bodyEnd:
            if curr.str in ('case', 'default'):
                group: list[Token] = [curr]
                temp: Token | None = curr.next
                while temp and temp is not scope.bodyEnd and temp.str not in ('case', 'default'):
                    group.append(temp)
                    temp = temp.next
                groups.append(group)
                curr = temp
                continue
            curr = curr.next

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
