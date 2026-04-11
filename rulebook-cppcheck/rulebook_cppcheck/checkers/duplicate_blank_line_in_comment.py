from re import Pattern, compile as re_compile

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class DuplicateBlankLineInCommentChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line-in-comment"""
    ID: str = 'duplicate-blank-line-in-comment'
    _MSG: str = 'duplicate.blank.line.in.comment'

    _EMPTY_COMMENT_REGEX: Pattern = re_compile(r'^\s*//\s*$')

    def check_file(self, token: Token, content: str) -> None:
        lines: list[str] = content.splitlines()
        for i in range(len(lines) - 1):
            # checks for violation
            if not self._EMPTY_COMMENT_REGEX.match(lines[i]) or \
                not self._EMPTY_COMMENT_REGEX.match(lines[i + 1]):
                continue
            self.report_error(token, Messages.get(self._MSG), i + 2)
