from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class ChainCallWrapChecker(RulebookTokenChecker):
    ID: str = 'chain-call-wrap'
    MSG_MISSING: str = 'chain.call.wrap.missing'
    MSG_UNEXPECTED: str = 'chain.call.wrap.unexpected'

    @override
    def process_token(self, token: Token) -> None:
        if token.str != '.':
            return

        prev_token: Token | None = token.previous
        if not prev_token:
            return

        #  checks for violation
        if prev_token.str in {')', '}'}:
            if token.linenr != prev_token.linenr:
                if prev_token.previous and \
                    prev_token.linenr != prev_token.previous.linenr:
                    self.report_error(token, _Messages.get(self.MSG_UNEXPECTED))
                    return
        if token.linenr != prev_token.linenr:
            return
        curr_token: Token | None = token.next
        is_multiline_chain: bool = False
        while curr_token:
            if curr_token.str == '.' and \
                curr_token.linenr != token.linenr:
                is_multiline_chain = True
                break
            if curr_token.str in {';', '{', '}'}:
                break
            curr_token = curr_token.next
        if not is_multiline_chain:
            return
        self.report_error(token, _Messages.get(self.MSG_MISSING))
