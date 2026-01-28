from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Configuration
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Configuration


class LineLengthChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#line-length"""
    MSG: str = 'line.length'
    ARG_MAX_LINE_LENGTH: str = 'max-line-length'

    id: str = 'line-length'
    args = [ARG_MAX_LINE_LENGTH]

    max_line_length: int = 100

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self.max_line_length: int = int(args[self.ARG_MAX_LINE_LENGTH])

    @override
    def run_check(self, configuration: Configuration) -> None:
        processed_lines: dict[str, set[int]] = {}
        for token in configuration.tokenlist:
            if token.file not in processed_lines:
                processed_lines[token.file] = set()
            if token.linenr in processed_lines[token.file]:
                continue
            processed_lines[token.file].add(token.linenr)
            with open(token.file, 'r', encoding='UTF-8') as f:
                for i, line in enumerate(f, 1):
                    if i != token.linenr:
                        continue
                    if len(line.rstrip('\r\n')) > self.max_line_length:
                        self.report_error(token, _Messages.get(self.MSG, self.max_line_length))
                    break
