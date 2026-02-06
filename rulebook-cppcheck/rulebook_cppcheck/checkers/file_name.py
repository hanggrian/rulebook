from re import Pattern, compile as re
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class FileNameChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#file-name"""
    ID: str = 'file-name'
    MSG: str = 'file.name'

    SNAKE_CASE_REGEX: Pattern = re(r'^[a-z0-9_]+\.(c|cpp)$')

    @override
    def check_file(self, token: Token, content: str) -> None:
        # checks for violation
        filename: str = token.file.split('/')[-1]
        if self.SNAKE_CASE_REGEX.match(filename):
            return
        self.report_error(
            token,
            _Messages.get(self.MSG, filename.lower().replace('-', '_')),
        )
