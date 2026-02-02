from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.nodes import _next_sibling

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class RequiredGenericsNameChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#required-generics-name"""
    ID: str = 'required-generics-name'
    MSG: str = 'required.generics.name'
    ARG_REQUIRED_GENERICS_NAMES: str = 'required-generics-names'
    ARGS = [ARG_REQUIRED_GENERICS_NAMES]

    def __init__(self):
        super().__init__()
        self._required_generics_names: set[str] = {'E', 'N', 'K', 'T', 'V'}

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self._required_generics_names = \
            set(args[self.ARG_REQUIRED_GENERICS_NAMES].split(','))

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
        if keyword_token.str not in ('typename', 'class'):
            return
        if name_token.type != 'name' or \
            name_token.str == '...':
            return
        if name_token.str in self._required_generics_names:
            return
        self.report_error(
            name_token,
            _Messages.get(self.MSG, ', '.join(sorted(self._required_generics_names))),
        )
