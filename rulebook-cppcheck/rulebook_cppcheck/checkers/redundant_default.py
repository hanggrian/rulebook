from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _next_sibling

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class RedundantDefaultChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#redundant-default"""
    ID: str = 'redundant-default'
    MSG: str = 'redundant.default'

    BREAK_STATEMENTS: set[str] = {'return', 'continue', 'throw', 'goto'}

    @override
    def process_token(self, token: Token) -> None:
        if token.str != 'switch':
            return

        # find the opening brace of the switch
        l_brace: Token | None = token.next
        if l_brace and l_brace.str == '(':
            l_brace = l_brace.link.next
        if not l_brace or l_brace.str != '{':
            return
        r_brace: Token | None = l_brace.link
        curr_token: Token | None = l_brace.next
        cases: list[Token] = []
        default_token: Token | None = None

        # find default
        while curr_token and curr_token is not r_brace:
            if curr_token.str == 'case':
                curr_token = _next_sibling(curr_token, lambda t: t is r_brace or t.str == ':')
                if curr_token and curr_token.str == ':':
                    cases.append(curr_token)
            elif curr_token.str == 'default':
                default_token = _next_sibling(curr_token, lambda t: t is r_brace or t.str == ':')
            curr_token = curr_token.next
        if not default_token or not cases:
            return

        # checks for violation
        for i, case_colon in enumerate(cases):
            limit: Token = cases[i + 1] if i + 1 < len(cases) else default_token
            has_jump: bool = False
            search: Token | None = case_colon.next
            while search and search is not limit:
                if search.str in self.BREAK_STATEMENTS:
                    has_jump = True
                    break
                search = search.next
            if not has_jump:
                return
        self.report_error(default_token, _Messages.get(self.MSG))
