from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class ChainCallWrapChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#chain-call-wrap"""
    ID: str = 'chain-call-wrap'
    MSG_MISSING: str = 'chain.call.wrap.missing'
    MSG_UNEXPECTED: str = 'chain.call.wrap.unexpected'

    SHOULD_BREAK: set[str] = {';', '{', '}', '='}
    CLOSING_PARENTHESES: set[str] = {')', '}'}

    @override
    def process_token(self, token: Token) -> None:
        # target root chain call
        if token.str != '.' or (token.previous and token.previous.str == '.'):
            return

        # collect dots
        dots: list[Token] = []
        curr: Token | None = token
        depth: int = 0
        while curr:
            if curr.str == '(':
                depth += 1
            elif curr.str == ')':
                depth -= 1
            if depth < 0:
                break
            if depth == 0:
                if curr.str == '.':
                    dots.append(curr)
                elif curr.str in self.SHOULD_BREAK:
                    break
            curr = curr.next

        # skip dots in single-line
        if len(dots) < 2:
            return
        first_dot: Token = dots[0]
        if all(d.linenr == first_dot.linenr for d in dots):
            return

        # checks for violation
        for dot in dots:
            prev_token: Token | None = dot.previous
            if not prev_token:
                continue
            if prev_token.str in self.CLOSING_PARENTHESES and \
                prev_token.previous and \
                prev_token.linenr > prev_token.previous.linenr:
                if dot.linenr > prev_token.linenr:
                    self.report_error(dot, _Messages.get(self.MSG_UNEXPECTED))
                continue
            if dot.linenr == prev_token.linenr:
                self.report_error(dot, _Messages.get(self.MSG_MISSING))
