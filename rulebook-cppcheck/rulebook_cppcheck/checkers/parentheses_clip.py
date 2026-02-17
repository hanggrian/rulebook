from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class ParenthesesClipChecker(RulebookTokenChecker):
    ID: str = 'parentheses-clip'
    _MSG: str = 'parentheses.clip'

    _PARENTHESES: dict[str, str] = {
        '{': '}',
        '(': ')',
        '[': ']',
    }
    _MULTI_BLOCKS: set[str] = {'if', 'else', 'while', 'for', 'switch', 'try', 'catch', 'do'}
    _MULTI_BLOCK_STOPS: set[str] = {';', '{', '}'}

    def __init__(self):
        super().__init__()
        self._reported_errors: set[tuple[str, int, int, str]] = set()

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        for token in tokens:
            token_str: str = token.str
            if token_str not in self._PARENTHESES or \
                token.isExpandedMacro or \
                (token_str == '(' and token.isRemovedVoidParameter):
                continue
            closing_token: Token | None = token.link
            if not closing_token or \
                token.next != closing_token:
                continue
            if token.linenr == closing_token.linenr and \
                token.column + len(token_str) == closing_token.column:
                continue
            if token_str == '(':
                if token.astParentId:
                    continue
            elif token_str == '{':
                if token.astParentId and token.astParentId != '0':
                    continue
                prev: Token | None = token.previous
                is_control: bool = False
                while prev:
                    if prev.str in self._MULTI_BLOCKS:
                        is_control = True
                        break
                    if prev.str in self._MULTI_BLOCK_STOPS:
                        break
                    prev = prev.previous
                if is_control:
                    continue
            self._report_error_once(
                token,
                _Messages.get(self._MSG, token_str + self._PARENTHESES[token_str]),
            )

    def _report_error_once(self, token: Token, message: str) -> None:
        error_key: tuple[str, int, int, str] = (token.file, token.linenr, token.column, message)
        if error_key in self._reported_errors:
            return
        self._reported_errors.add(error_key)
        self.report_error(token, message)
