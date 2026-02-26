from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Scope, Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Scope, Token


# TODO implement logic for test_branches_with_comment_are_considered_multiline
class CaseSeparatorChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#case-separator"""
    ID: str = 'case-separator'
    _MSG_MISSING: str = 'case.separator.missing'
    _MSG_UNEXPECTED: str = 'case.separator.unexpected'

    _BRANCH_TOKENS: set[str] = {'case', 'default'}

    def __init__(self) -> None:
        super().__init__()
        self._reported_errors: set[tuple[str, int, int, str]] = set()

    @override
    def get_scopeset(self) -> set[str]:
        return {'Switch'}

    @override
    def visit_scope(self, scope: Scope) -> None:
        # collect cases
        body_start: Token | None = scope.bodyStart
        body_end: Token | None = scope.bodyEnd
        if body_start is None or body_end is None:
            return

        groups: list[list[Token]] = []
        curr_token: Token | None = body_start.next

        while curr_token is not None and curr_token is not body_end:
            if curr_token.str in self._BRANCH_TOKENS:
                case_keyword: Token = curr_token
                case_label: Token = curr_token.next if curr_token.str == 'case' else curr_token

                end_token: Token = case_keyword
                scan: Token | None = curr_token.next
                colon_found: bool = False
                has_body: bool = False

                while scan is not None and scan is not body_end:
                    if scan.str in self._BRANCH_TOKENS and scan.scope == scope:
                        break
                    if scan.str == ':':
                        colon_found = True
                    elif colon_found:
                        has_body = True
                    end_token = scan
                    scan = scan.next

                if not has_body and \
                    scan is not None and \
                    scan is not body_end and \
                    scan.str in self._BRANCH_TOKENS:
                    end_token = scan

                groups.append([case_label, case_keyword, end_token])
                curr_token = scan
                continue
            curr_token = curr_token.next

        # checks for violation
        if not groups:
            return

        for i in range(1, len(groups)):
            prev_group: list[Token] = groups[i - 1]
            curr_group: list[Token] = groups[i]

            prev_is_multiline: bool = prev_group[2].linenr - prev_group[1].linenr > 0
            prev_has_body: bool = prev_group[2].str not in self._BRANCH_TOKENS

            if not prev_has_body:
                curr_is_single_line: bool = curr_group[2].linenr == curr_group[1].linenr
                if not curr_is_single_line:
                    continue

            prev_end_line: int = prev_group[2].linenr
            curr_start_line: int = curr_group[1].linenr
            line_diff: int = curr_start_line - prev_end_line

            if prev_is_multiline:
                if line_diff != 2:
                    self._report_error_once(prev_group[0], _Messages.get(self._MSG_MISSING))
            elif line_diff != 1:
                self._report_error_once(prev_group[0], _Messages.get(self._MSG_UNEXPECTED))

    def _report_error_once(self, token: Token, message: str) -> None:
        error_key: tuple[str, int, int, str] = (token.file, token.linenr, token.column, message)
        if error_key in self._reported_errors:
            return
        self._reported_errors.add(error_key)
        self.report_error(token, message)
