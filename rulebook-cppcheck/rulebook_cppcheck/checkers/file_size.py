from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class FileSizeChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#file-size"""
    MSG: str = 'file.size'
    ARG_MAX_FILE_SIZE: str = 'max-file-size'

    id: str = 'file-size'
    args = [ARG_MAX_FILE_SIZE]

    max_file_size: int = 1000

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self.max_file_size: int = int(args[self.ARG_MAX_FILE_SIZE])

    @override
    def check_file(self, token: Token, content: str) -> None:
        if len(content.splitlines()) > self.max_file_size:
            self.report_error(token, _Messages.get(self.MSG, self.max_file_size))
