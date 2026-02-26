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
    _MSG: str = 'redundant.default'

    _BREAK_STATEMENTS: set[str] = {'return', 'continue', 'throw', 'goto'}

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        for token in [t for t in tokens if t.str == 'switch']:
            # find the opening brace of the switch
            l_brace: Token | None = token.next
            if l_brace is not None and l_brace.str == '(':
                l_brace = l_brace.link.next
            if l_brace is None or l_brace.str != '{':
                continue

            # find default
            default_token, cases = self._get_default(l_brace.next, l_brace.link)
            if default_token is None or not cases:
                continue

            # checks for violation
            continue_outer: bool = False
            for i, case_colon in enumerate(cases):
                limit: Token = cases[i + 1] if i + 1 < len(cases) else default_token
                has_jump: bool = False
                search: Token | None = case_colon.next
                while search and search is not limit:
                    if search.str in self._BREAK_STATEMENTS:
                        has_jump = True
                        break
                    search = search.next
                if not has_jump:
                    continue_outer = True
                    break
            if continue_outer:
                continue
            self.report_error(default_token, _Messages.get(self._MSG))

    @staticmethod
    def _get_default(
        curr_token: Token | None,
        r_brace: Token | None,
    ) -> tuple[Token, list[Token]] | None:
        cases: list[Token] = []
        default_token: Token | None = None
        while curr_token is not None and curr_token is not r_brace:
            if curr_token.str == 'case':
                curr_token = _next_sibling(curr_token, lambda t: t is r_brace or t.str == ':')
                if curr_token and curr_token.str == ':':
                    cases.append(curr_token)
            elif curr_token.str == 'default':
                default_token = \
                    _next_sibling(curr_token, lambda t: t is r_brace or t.str == ':')
            curr_token = curr_token.next
        return default_token, cases
