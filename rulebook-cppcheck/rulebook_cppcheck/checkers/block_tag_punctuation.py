from re import DOTALL, finditer
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class BlockTagPunctuationChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#block-tag-punctuation"""
    ID: str = 'block-tag-punctuation'
    MSG: str = 'block.tag.punctuation'
    ARG_BLOCK_TAGS: str = 'block-tags'
    ARGS = [ARG_BLOCK_TAGS]

    PUNCTUATIONS: set[str] = {'.', '!', '?', ')'}

    def __init__(self):
        super().__init__()
        self._block_tags: set[str] = {
            '@param',
            '@return',
            '@returns',
        }

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self._block_tags = set(args[self.ARG_BLOCK_TAGS].split(','))

    @override
    def check_file(self, token: Token, content: str) -> None:
        for match in finditer(r'/\*(.*?)\*/', content, DOTALL):
            comment_body: str = match.group(1)
            start_line: int = content.count('\n', 0, match.start()) + 1
            lines: list[str] = comment_body.splitlines()
            for i, line in enumerate(lines):
                stripped: str = line.strip().lstrip('*').strip()
                if not stripped:
                    continue
                for tag in self._block_tags:
                    if not stripped.startswith(tag):
                        continue
                    description: str = stripped[len(tag):].strip()
                    if not description:
                        continue
                    if description[-1] in self.PUNCTUATIONS:
                        continue
                    self.report_error(token, _Messages.get(self.MSG, tag), start_line + i)
