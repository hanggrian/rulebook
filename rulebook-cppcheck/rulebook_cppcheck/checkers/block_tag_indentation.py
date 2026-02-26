from re import DOTALL, Match, Pattern, compile as re_compile, finditer
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class BlockTagIndentationChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#block-tag-indentation"""
    ID: str = 'block-tag-indentation'
    _MSG: str = 'block.tag.indentation'

    _BLOCK_TAG_REGEX: Pattern = re_compile(r'^\s*\*\s+@\w+')
    _CONTINUATION_LINE_REGEX: Pattern = re_compile(r'^\s*\*(\s+)')

    @override
    def check_file(self, token: Token, content: str) -> None:
        for match in finditer(r'/\*\*.*?\*/', content, DOTALL):
            comment_text: str = match.group()
            start_line: int = content.count('\n', 0, match.start()) + 1
            lines: list[str] = comment_text.splitlines()
            in_block_tag: bool = False
            for i, line in enumerate(lines):
                if self._BLOCK_TAG_REGEX.search(line):
                    in_block_tag = True
                    continue
                if not in_block_tag:
                    continue
                stripped: str = line.strip()
                if not stripped or stripped == '*' or stripped == '*/':
                    in_block_tag = False
                    continue
                if stripped.startswith('* @'):
                    in_block_tag = True
                    continue

                match_indent: Match | None = self._CONTINUATION_LINE_REGEX.match(line)
                if match_indent is None:
                    continue
                indent_size: int = len(match_indent.group(1))
                if indent_size == 5:
                    continue
                self.report_error(token, _Messages.get(self._MSG), start_line + i)
