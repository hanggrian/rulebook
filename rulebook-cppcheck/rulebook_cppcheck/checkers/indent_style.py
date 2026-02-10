from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.options import INDENT_STYLE_OPTION

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class IndentStyleChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#indent-style"""
    ID: str = 'indent-style'
    MSG: str = 'indent.style'
    ARGS: list[str] = [INDENT_STYLE_OPTION]

    def __init__(self):
        super().__init__()
        self._indent_size: int = 4
        self._processed_lines: dict[str, set[int]] = {}

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self._indent_size = int(args[INDENT_STYLE_OPTION])

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
                line_stripped: str = line.lstrip(' ')
                if not line_stripped.strip() or line_stripped.startswith('*'):
                    continue
                leading_spaces: int = len(line) - len(line_stripped)
                if leading_spaces % self._indent_size != 0:
                    self.report_error(token, _Messages.get(self.MSG, self._indent_size))
