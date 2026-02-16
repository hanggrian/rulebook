from re import DOTALL, finditer
from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import _Messages
from rulebook_cppcheck.options import PUNCTUATE_BLOCK_TAGS_OPTION

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class BlockTagPunctuationChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#block-tag-punctuation"""
    ID: str = 'block-tag-punctuation'
    _MSG: str = 'block.tag.punctuation'
    ARGS: list[str] = [PUNCTUATE_BLOCK_TAGS_OPTION]

    _PUNCTUATIONS: set[str] = {'.', '!', '?', ')'}

    def __init__(self):
        super().__init__()
        self._block_tags: set[str] = {
            '@param',
            '@return',
            '@returns',
        }

    @override
    def before_run(self, args: dict[str, str]) -> None:
        self._block_tags = set(args[PUNCTUATE_BLOCK_TAGS_OPTION].split(','))

    @override
    def check_file(self, token: Token, content: str) -> None:
        for match in finditer(r'/\*(.*?)\*/', content, DOTALL):
            comment_body: str = match.group(1)
            start_line: int = content.count('\n', 0, match.start())
            lines: list[str] = comment_body.splitlines()

            # strategy to capture continuation indent
            current_tag: str | None = None
            last_text: str = ''
            last_text_line_idx: int = -1

            for i, line in enumerate(lines):
                stripped: str = line.strip().lstrip('*').strip()
                if not stripped:
                    continue

                # only enforce certain tags
                found_tag: str = \
                    next((t for t in self._block_tags if stripped.startswith(t)), None)

                # long descriptions have multiple lines, take only the last one
                if found_tag:
                    if current_tag and last_text and last_text[-1] not in self._PUNCTUATIONS:
                        self.report_error(
                            token,
                            _Messages.get(self._MSG, current_tag),
                            start_line + last_text_line_idx,
                        )

                    current_tag = found_tag
                    remainder: str = stripped[len(found_tag):].strip()
                    if current_tag == '@param':
                        parts: list[str] = remainder.split(maxsplit=1)
                        last_text = parts[1] if len(parts) > 1 else ''
                    else:
                        last_text = remainder
                    last_text_line_idx = i if last_text else -1
                elif current_tag:
                    last_text = stripped
                    last_text_line_idx = i

            # checks for violation
            if not current_tag or \
                not last_text or \
                last_text[-1] in self._PUNCTUATIONS:
                continue
            self.report_error(
                token,
                _Messages.get(self._MSG, current_tag),
                start_line + last_text_line_idx,
            )
