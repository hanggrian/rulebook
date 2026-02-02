from typing import override

from regex import DOTALL, finditer, compile as regex, Match

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class TodoCommentChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#todo-comment"""
    ID: str = 'todo-comment'
    MSG_KEYWORD: str = 'todo.comment.keyword'
    MSG_SEPARATOR: str = 'todo.comment.separator'

    KEYWORD_REGEX = regex(r'\b(?i:fixme|todo)(?<!FIXME|TODO)\b')
    SEPARATOR_REGEX = regex(r'\b(?i:todo|fixme)([^ \t\n])')

    @override
    def check_file(self, token: Token, content: str) -> None:
        for match in finditer(r'//.*|/\*(.*?)\*/', content, DOTALL):
            comment_text: str = match.group(0)
            start_line: int = content.count('\n', 0, match.start()) + 1

            for i, line in enumerate(comment_text.splitlines()):
                line_no: int = start_line + i

                keyword_match: Match | None = self.KEYWORD_REGEX.search(line)
                if keyword_match:
                    self.report_error(
                        token,
                        _Messages.get(self.MSG_KEYWORD, keyword_match.group(0)),
                        line_no,
                    )

                separator_match: Match | None = self.SEPARATOR_REGEX.search(line)
                if not separator_match:
                    continue
                self.report_error(
                    token,
                    _Messages.get(self.MSG_SEPARATOR, separator_match.group(1)),
                    line_no,
                )
