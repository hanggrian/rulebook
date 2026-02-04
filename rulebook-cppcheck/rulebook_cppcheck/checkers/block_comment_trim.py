from re import DOTALL, finditer
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class BlockCommentTrimChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#block-comment-trim"""
    ID: str = 'block-comment-trim'
    MSG_FIRST: str = 'block.comment.trim.first'
    MSG_LAST: str = 'block.comment.trim.last'

    @override
    def check_file(self, token: Token, content: str) -> None:
        for match in finditer(r'/\*(.*?)\*/', content, DOTALL):
            comment_body: str = match.group(1)
            start_line: int = content.count('\n', 0, match.start()) + 1
            lines: list[str] = comment_body.splitlines()

            # filter out block comments that couldn't be trimmed
            if len(lines) < 3:
                continue

            # collect content
            content_indices = [
                i for i, line in enumerate(lines) if line.strip() not in {'*', ''}
            ]
            if not content_indices:
                continue

            # check first line
            first_line: str = lines[0].strip()
            second_line: str = lines[1].strip()
            if (first_line in {'*', ''}) and second_line == '*':
                if content_indices[0] > 1:
                    self.report_error(
                        token,
                        _Messages.get(self.MSG_FIRST),
                        start_line + 1,
                    )

            # check last line
            last_line: str = lines[-1].strip()
            penultimate_line: str = lines[-2].strip()
            if last_line != '' or penultimate_line != '*':
                continue
            if content_indices[-1] >= len(lines) - 2:
                continue
            self.report_error(
                token,
                _Messages.get(self.MSG_LAST),
                start_line + len(lines) - 1,
            )
