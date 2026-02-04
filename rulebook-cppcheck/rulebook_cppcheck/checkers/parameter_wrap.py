from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class ParameterWrapChecker(RulebookTokenChecker):
    ID: str = 'parameter-wrap'
    MSG: str = 'parameter.wrap'

    @override
    def process_token(self, token: Token) -> None:
        if token.str != '(':
            return

        # collect parameters
        params: list[Token] = []
        curr_token: Token | None = token.next
        depth: int = 1
        while curr_token and depth > 0:
            if curr_token.str == '(':
                depth += 1
            elif curr_token.str == ')':
                depth -= 1
            if depth == 1 and curr_token.str == ',':
                params.append(curr_token.next)
            elif depth == 1 and not params and curr_token.str != ')':
                params.append(curr_token)
            curr_token = curr_token.next
        if not params:
            return

        # checks for violation
        last: Token = params[-1]
        if token.linenr == last.linenr:
            return
        for i in range(1, len(params)):
            prev: Token = params[i - 1]
            curr_param: Token = params[i]
            if curr_param.linenr != prev.linenr:
                continue
            self.report_error(curr_param, _Messages.get(self.MSG))
