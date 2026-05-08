from re import Pattern, compile as re

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import Messages
from rulebook_cppcheck.nodes import next_sibling

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class GenericNameChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#generic-name"""
    ID: str = 'generic-name'
    _MSG: str = 'generic.name'

    _TARGET_TOKENS: frozenset[str] = frozenset(['typename', 'class'])
    _PASCAL_CASE_REGEX: Pattern = re(r'^[A-Z](?![A-Z0-9]+$)[a-zA-Z0-9]*$|^[A-Z]\d*$')

    def process_tokens(self, tokens: list[Token]) -> None:
        for token in [t for t in tokens if t.str == 'template']:
            # only target template declaration
            open_bracket: Token | None = next_sibling(token, lambda t: t.str == '<')
            if open_bracket is None or \
                not open_bracket.link:
                continue

            # checks for violation
            closing: Token | None = open_bracket.link
            keyword_token: Token | None = None
            curr_token: Token | None = open_bracket.next
            while curr_token is not None and \
                curr_token is not closing:
                if not isinstance(curr_token, Token):
                    curr_token = curr_token.next
                    continue
                if curr_token.str in self._TARGET_TOKENS:
                    keyword_token = curr_token
                elif keyword_token is not None and \
                    curr_token.str != ',' and \
                    curr_token.type == 'name' and \
                    curr_token.str != '...':
                    if not self._PASCAL_CASE_REGEX.match(curr_token.str):
                        self.report_error(curr_token, Messages.get(self._MSG))
                    keyword_token = None
                curr_token = curr_token.next
