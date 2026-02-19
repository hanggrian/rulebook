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
    _MSG: str = 'indent.style'
    ARGS: list[str] = [INDENT_STYLE_OPTION]

    def __init__(self) -> None:
        super().__init__()
        self._indent_size: int = 4

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self._indent_size = int(args[INDENT_STYLE_OPTION])

    @override
    def process_tokens(self, tokens: list[Token]) -> None:
        processed_lines: dict[str, set[int]] = {}
        all_lines: dict[str, list[str]] = {}
        for token in [t for t in tokens if t.file and t.linenr]:
            token_file: str | None = token.file
            token_linenr: int = token.linenr

            if token_file not in processed_lines:
                processed_lines[token_file] = set()
            if token_linenr in processed_lines[token_file]:
                continue
            if token_file not in all_lines:
                with open(token_file, 'r', encoding='UTF-8') as f:
                    all_lines[token_file] = f.readlines()
            processed_lines[token_file].add(token_linenr)
            line = all_lines[token_file][token_linenr - 1]
            line_stripped = line.lstrip(' ')
            if not line_stripped.strip() or \
                line_stripped.startswith('*') or \
                (len(line) - len(line_stripped)) % self._indent_size == 0:
                continue
            self.report_error(token, _Messages.get(self._MSG, self._indent_size))
