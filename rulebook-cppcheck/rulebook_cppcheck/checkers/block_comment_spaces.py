from re import DOTALL, Pattern, finditer, compile as re
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
    MSG_SINGLE_START: str = 'block.comment.spaces.single.start'
    MSG_SINGLE_END: str = 'block.comment.spaces.single.end'
    MSG_MULTI: str = 'block.comment.spaces.multi'

    BLOCK_COMMENT_START: Pattern = re(r'^/\*+[^/*\s]')
    BLOCK_COMMENT_CENTER: Pattern = re(r'^\s*\*[^\s/]')
    BLOCK_COMMENT_END: Pattern = re(r'[^\s\*]\*/$')

    @override
    def check_file(self, token: Token, content: str) -> None:
        for match in finditer(r'/\*.*?\*/', content, DOTALL):
            comment_text = match.group()
            start_line = content.count('\n', 0, match.start()) + 1
            lines = comment_text.splitlines()
            if self.BLOCK_COMMENT_START.search(lines[0]):
                self.report_error(
                    token,
                    _Messages.get(self.MSG_SINGLE_START),
                    line=start_line,
                )
            if self.BLOCK_COMMENT_END.search(lines[-1]):
                self.report_error(
                    token,
                    _Messages.get(self.MSG_SINGLE_END),
                    line=start_line + len(lines) - 1,
                )
            for i in range(1, len(lines)):
                if not self.BLOCK_COMMENT_CENTER.search(lines[i]):
                    continue
                self.report_error(
                    token,
                    _Messages.get(self.MSG_MULTI),
                    line=start_line + i,
                )
