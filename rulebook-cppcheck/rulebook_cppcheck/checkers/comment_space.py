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
            if '//' not in line_stripped:
                continue
            line_stripped = line_stripped.split('//')[1]
            if line_stripped.startswith(' ') or \
                line_stripped.replace('/', '').strip() == '':
                continue
            self.report_error(token, _Messages.get(self._MSG), lineno, line.find('//') + 1)
