from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Configuration
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Configuration


class IndentStyleChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#indent-style"""
    MSG: str = 'indent.style'
    ARG_INDENT_STYLE: str = 'indent-size'

    id: str = 'indent-style'
    args = [ARG_INDENT_STYLE]

    indent_size: int = 4

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self.indent_size: int = int(args[self.ARG_INDENT_STYLE])

    @override
    def run_check(self, configuration: Configuration) -> None:
        processed_lines: dict[str, set[int]] = {}
        for token in configuration.tokenlist:
            file_path: str = token.file
            line_nr: int = token.linenr
            if file_path not in processed_lines:
                processed_lines[file_path] = set()
            if line_nr in processed_lines[file_path]:
                continue
            processed_lines[file_path].add(line_nr)
            with open(file_path, 'r', encoding='UTF-8') as f:
                for i, line in enumerate(f, 1):
                    if i != line_nr:
                        continue
                    leading_spaces: int = len(line) - len(line.lstrip(' '))
                    if leading_spaces % self.indent_size != 0:
                        self.report_error(token, _Messages.get(self.MSG, self.indent_size))
                    break
