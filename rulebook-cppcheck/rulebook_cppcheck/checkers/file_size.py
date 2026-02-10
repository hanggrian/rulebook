from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.options import MAX_FILE_SIZE_OPTION

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class FileSizeChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#file-size"""
    ID: str = 'file-size'
    MSG: str = 'file.size'
    ARGS: list[str] = [MAX_FILE_SIZE_OPTION]

    def __init__(self):
        super().__init__()
        self._max_file_size: int = 1000

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self._max_file_size = int(args[MAX_FILE_SIZE_OPTION])

    @override
    def check_file(self, token: Token, content: str) -> None:
        # checks for violation
        if len(content.splitlines()) <= self._max_file_size:
            return
        self.report_error(token, _Messages.get(self.MSG, self._max_file_size))
