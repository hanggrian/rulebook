from re import DOTALL, Pattern, compile as re, finditer
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class BlockCommentSpacesChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#block-comment-spaces"""
    ID: str = 'block-comment-spaces'
    _MSG_SINGLE_START: str = 'block.comment.spaces.single.start'
    _MSG_SINGLE_END: str = 'block.comment.spaces.single.end'
    _MSG_MULTI: str = 'block.comment.spaces.multi'

    _BLOCK_COMMENT_START_REGEX: Pattern = re(r'^/\*+[^/*\s]')
    _BLOCK_COMMENT_CENTER_REGEX: Pattern = re(r'^\s*\*[^\s/]')
    _BLOCK_COMMENT_END_REGEX: Pattern = re(r'[^\s\*]\*/$')

    @override
    def check_file(self, token: Token, content: str) -> None:
        for match in finditer(r'/\*.*?\*/', content, DOTALL):
            comment_text: str = match.group()
            start_line: int = content.count('\n', 0, match.start()) + 1
            lines: list[str] = comment_text.splitlines()
            if self._BLOCK_COMMENT_START_REGEX.search(lines[0]):
                self.report_error(
                    token,
                    _Messages.get(self._MSG_SINGLE_START),
                    start_line,
                )
            if self._BLOCK_COMMENT_END_REGEX.search(lines[-1]):
                self.report_error(
                    token,
                    _Messages.get(self._MSG_SINGLE_END),
                    start_line + len(lines) - 1,
                )
            for i in range(1, len(lines)):
                if not self._BLOCK_COMMENT_CENTER_REGEX.search(lines[i]):
                    continue
                self.report_error(
                    token,
                    _Messages.get(self._MSG_MULTI),
                    start_line + i,
                )
