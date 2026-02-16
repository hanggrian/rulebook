from re import Pattern, compile as re
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class CommentTrimChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#comment-trim"""
    ID: str = 'comment-trim'
    _MSG: str = 'comment.trim'

    _EOL_COMMENT_REGEX: Pattern = re(r'^\s*//\s*$')
    _ANY_COMMENT_REGEX: Pattern = re(r'^\s*//')

    @override
    def check_file(self, token: Token, content: str) -> None:
        lines: list[str] = content.splitlines()
        i: int = 0
        while i < len(lines):
            # find start of a comment block
            if self._ANY_COMMENT_REGEX.match(lines[i]):
                start_block: int = i
                # find end of the block
                j: int = i
                while j + 1 < len(lines) and \
                    self._ANY_COMMENT_REGEX.match(lines[j + 1]):
                    j += 1

                # check first line of block
                if start_block != j and \
                    self._EOL_COMMENT_REGEX.match(lines[start_block]):
                    self.report_error(token, _Messages.get(self._MSG), start_block + 1)

                # check last line of block
                if start_block != j and \
                    self._EOL_COMMENT_REGEX.match(lines[j]):
                    self.report_error(token, _Messages.get(self._MSG), j + 1)

                # skip to end of block
                i = j
            i += 1
