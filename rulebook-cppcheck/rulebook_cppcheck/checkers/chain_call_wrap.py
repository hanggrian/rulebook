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
    _MSG_MISSING: str = 'chain.call.wrap.missing'
    _MSG_UNEXPECTED: str = 'chain.call.wrap.unexpected'

    _SHOULD_BREAK: set[str] = {';', '{', '}', '='}
    _CLOSING_PARENTHESES: set[str] = {')', '}'}

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        for token in [
            t for t in tokens if t.str == '.' and (not t.previous or t.previous.str != '.')
        ]:
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
                    elif curr.str in self._SHOULD_BREAK:
                        break
                curr = curr.next

            # skip dots in single-line
            if len(dots) < 2:
                continue
            first_dot: Token = dots[0]
            if all(d.linenr == first_dot.linenr for d in dots):
                continue

            # checks for violation
            for dot in dots:
                prev_token: Token | None = dot.previous
                if not prev_token:
                    continue
                if prev_token.str in self._CLOSING_PARENTHESES and \
                    prev_token.previous and \
                    prev_token.linenr > prev_token.previous.linenr:
                    if dot.linenr > prev_token.linenr:
                        self.report_error(dot, _Messages.get(self._MSG_UNEXPECTED))
                    continue
                if dot.linenr == prev_token.linenr:
                    self.report_error(dot, _Messages.get(self._MSG_MISSING))
