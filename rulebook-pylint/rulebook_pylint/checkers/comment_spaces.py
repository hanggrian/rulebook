from __future__ import annotations

from tokenize import COMMENT, TokenInfo

from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class CommentSpacesChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#comment-spaces"""
    _MSG: str = 'comment.spaces'

    name: str = 'comment-spaces'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        # checks for violation
        for token in [t for t in tokens if t.type == COMMENT]:
            if token.string.startswith('# ') or \
                token.string.startswith('#!') or \
                all(c == '#' for c in token.string):
                continue
            self.add_message(self._MSG, line=token.start[0], col_offset=token.start[1])


def register(linter: PyLinter) -> None:
    linter.register_checker(CommentSpacesChecker(linter))
