from regex import DOTALL, Match, Pattern, compile as regex, finditer

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


# pylint: disable=todo-comment
class TodoCommentChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#todo-comment"""
    ID: str = 'todo-comment'
    _MSG_KEYWORD: str = 'todo.comment.keyword'
    _MSG_SEPARATOR: str = 'todo.comment.separator'

    _KEYWORD_REGEX: Pattern = regex(r'\b(?i:fixme|todo)(?<!FIXME|TODO)\b')
    _SEPARATOR_REGEX: Pattern = regex(r'\b(?i:todo|fixme)([^ \t\n])')

    def check_file(self, token: Token, content: str) -> None:
        for match in finditer(r'//.*|/\*(.*?)\*/', content, DOTALL):
            # obtain comment content
            comment_text: str = match.group(0)
            start_line: int = content.count('\n', 0, match.start()) + 1

            for i, line in enumerate(comment_text.splitlines()):
                # checks for violation
                line_no: int = start_line + i
                keyword_match: Match | None = self._KEYWORD_REGEX.search(line)
                if keyword_match is not None:
                    self.report_error(
                        token,
                        _Messages.get(self._MSG_KEYWORD, keyword_match.group(0)),
                        line_no,
                    )
                separator_match: Match | None = self._SEPARATOR_REGEX.search(line)
                if separator_match is None:
                    continue
                self.report_error(
                    token,
                    _Messages.get(self._MSG_SEPARATOR, separator_match.group(1)),
                    line_no,
                )
