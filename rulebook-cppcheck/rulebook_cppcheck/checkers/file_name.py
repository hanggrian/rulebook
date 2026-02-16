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
    _MSG: str = 'file.name'

    _SNAKE_CASE_REGEX: Pattern = re(r'^[a-z0-9_]+\.(c|cpp|h|hpp)$')

    @override
    def check_file(self, token: Token, content: str) -> None:
        # checks for violation
        filename: str = token.file.split('/')[-1]
        if '_' in filename:  # remove prefix 'XXXXX_file.c' likely from dump
            filename = filename.split('_')[-1]
        if self._SNAKE_CASE_REGEX.match(filename):
            return
        self.report_error(
            token,
            _Messages.get(self._MSG, filename.lower().replace('-', '_')),
        )
