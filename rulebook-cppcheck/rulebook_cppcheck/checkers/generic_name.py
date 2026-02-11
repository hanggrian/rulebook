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
    MSG: str = 'generic.name'

    TARGET_TOKENS: set[str] = {'typename', 'class'}

    @override
    def process_token(self, token: Token) -> None:
        # only target template declaration
        if token.str != 'template':
            return
        open_bracket: Token | None = _next_sibling(token, lambda t: t.str == '<')
        if not open_bracket or not open_bracket.link:
            return
        closing: Token | None = open_bracket.link
        params: list[Token] = []
        curr_token: Token | None = open_bracket.next
        while curr_token and curr_token is not closing:
            if curr_token.str == ',':
                return
            params.append(curr_token)
            curr_token = curr_token.next
        if len(params) != 2:
            return

        # checks for violation
        keyword_token: Token = params[0]
        name_token: Token = params[1]
        if keyword_token.str not in self.TARGET_TOKENS:
            return
        name: str = name_token.str
        if name_token.type != 'name' or name == '...':
            return
        if len(name) == 1 and name[0].isupper():
            return
        self.report_error(name_token, _Messages.get(self.MSG))
