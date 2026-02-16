from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.options import MAX_LINE_LENGTH_OPTION

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class LineLengthChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#line-length"""
    ID: str = 'line-length'
    _MSG: str = 'line.length'
    ARGS: list[str] = [MAX_LINE_LENGTH_OPTION]

    def __init__(self):
        super().__init__()
        self._max_line_length: int = 100

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self._max_line_length = int(args[MAX_LINE_LENGTH_OPTION])

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        processed_lines: dict[str, set[int]] = {}
        cache: dict[str, list[str]] = {}
        for token in [t for t in tokens if t.file and t.linenr]:
            token_file: str = token.file
            token_linenr: int = token.linenr

            if token_file not in processed_lines:
                processed_lines[token_file] = set()
            if token_linenr in processed_lines[token_file]:
                continue
            if token_file not in cache:
                with open(token_file, 'r', encoding='UTF-8') as f:
                    cache[token_file] = f.readlines()
            processed_lines[token_file].add(token_linenr)
            if len(cache[token_file][token_linenr - 1].rstrip('\r\n')) <= self._max_line_length:
                continue
            self.report_error(token, _Messages.get(self._MSG, self._max_line_length))
