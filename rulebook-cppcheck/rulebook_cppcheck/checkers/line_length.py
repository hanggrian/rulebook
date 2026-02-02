from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class LineLengthChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#line-length"""
    ID: str = 'line-length'
    MSG: str = 'line.length'
    ARG_MAX_LINE_LENGTH: str = 'max-line-length'
    ARGS = [ARG_MAX_LINE_LENGTH]

    def __init__(self):
        super().__init__()
        self._max_line_length: int = 100
        self._processed_lines: dict[str, set[int]] = {}

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self._max_line_length = int(args[self.ARG_MAX_LINE_LENGTH])

    @override
    def process_token(self, token: Token) -> None:
        file_path: str = token.file
        line_nr: int = token.linenr
        if file_path not in self._processed_lines:
            self._processed_lines[file_path] = set()
        if line_nr in self._processed_lines[file_path]:
            return
        self._processed_lines[file_path].add(line_nr)
        with open(file_path, 'r', encoding='UTF-8') as f:
            for i, line in enumerate(f, 1):
                if i != line_nr:
                    continue
                if len(line.rstrip('\r\n')) <= self._max_line_length:
                    continue
                self.report_error(token, _Messages.get(self.MSG, self._max_line_length))
