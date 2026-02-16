from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _next_sibling

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class GenericNameChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#generic-name"""
    ID: str = 'generic-name'
    _MSG: str = 'generic.name'

    _TARGET_TOKENS: set[str] = {'typename', 'class'}

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        for token in [t for t in tokens if t.str == 'template']:
            open_bracket: Token | None = _next_sibling(token, lambda t: t.str == '<')
            if not open_bracket or not open_bracket.link:
                continue
            closing: Token | None = open_bracket.link
            params: list[Token] = []
            curr_token: Token | None = open_bracket.next
            continue_outer: bool = False
            while curr_token and curr_token is not closing:
                if curr_token.str == ',':
                    continue_outer = True
                    break
                params.append(curr_token)
                curr_token = curr_token.next
            if continue_outer or len(params) != 2:
                continue

            # checks for violation
            keyword_token: Token = params[0]
            name_token: Token = params[1]
            if keyword_token.str not in self._TARGET_TOKENS:
                continue
            name: str = name_token.str
            if name_token.type != 'name' or name == '...':
                continue
            if len(name) == 1 and name[0].isupper():
                continue
            self.report_error(name_token, _Messages.get(self._MSG))
