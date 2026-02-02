from re import Pattern, compile as re_compile
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class DuplicateBlankLineInCommentChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line-in-comment"""
    ID: str = 'duplicate-blank-line-in-comment'
    MSG: str = 'duplicate.blank.line.in.comment'

    EMPTY_COMMENT: Pattern = re_compile(r'^\s*//\s*$')

    @override
    def check_file(self, token: Token, content: str) -> None:
        lines: list[str] = content.splitlines()
        for i in range(len(lines) - 1):
            if not self.EMPTY_COMMENT.match(lines[i]) or \
                not self.EMPTY_COMMENT.match(lines[i + 1]):
                continue
            self.report_error(token, _Messages.get(self.MSG), i + 2)
