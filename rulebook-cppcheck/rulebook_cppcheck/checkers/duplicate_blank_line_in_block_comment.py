from re import DOTALL, finditer
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class DuplicateBlankLineInBlockCommentChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line-in-block-comment"""
    ID: str = 'duplicate-blank-line-in-block-comment'
    _MSG: str = 'duplicate.blank.line.in.block.comment'

    @override
    def check_file(self, token: Token, content: str) -> None:
        for match in finditer(r'/\*(.*?)\*/', content, DOTALL):
            comment_body: str = match.group(1)
            start_line: int = content.count('\n', 0, match.start()) + 1
            lines: list[str] = comment_body.splitlines()
            for i in range(len(lines) - 1):
                curr_token: str = lines[i].strip()
                next_token: str = lines[i + 1].strip()
                if curr_token != '*' or next_token != '*':
                    continue
                if i == 0 or i + 1 == len(lines) - 1:
                    continue
                self.report_error(token, _Messages.get(self._MSG), start_line + i + 1)
