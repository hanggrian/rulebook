from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class CommentSpaceChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#comment-space"""
    ID: str = 'comment-space'
    _MSG: str = 'comment.space'

    @override
    def check_file(self, token: Token, content: str) -> None:
        # checks for violation
        for lineno, line in enumerate(content.splitlines(), 1):
            line_stripped: str = line.lstrip()
            if line_stripped.startswith('*'):
                continue
            if '//' not in line_stripped:
                continue
            comment_pos: int = line.find('//')
            before_comment: str = line[:comment_pos]
            double_quote_count: int = 0
            single_quote_count: int = 0
            i: int = 0
            while i < len(before_comment):
                if before_comment[i] == '\\':
                    i += 2
                    continue
                if before_comment[i] == '"':
                    double_quote_count += 1
                elif before_comment[i] == "'":
                    single_quote_count += 1
                i += 1
            if double_quote_count % 2 == 1 or single_quote_count % 2 == 1:
                continue
            line_stripped = line_stripped.split('//')[1]
            if line_stripped.startswith(' ') or \
                line_stripped.replace('/', '').strip() == '':
                continue
            self.report_error(token, _Messages.get(self._MSG), lineno, line.find('//') + 1)
