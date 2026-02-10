from __future__ import annotations

from tokenize import TokenInfo, COMMENT

from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class CommentSpaceChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#comment-space"""
    MSG: str = 'comment.space'

    name: str = 'comment-space'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        # checks for violation
        for token in [t for t in tokens if t.type is COMMENT]:
            token_str: str = token.string
            if token_str.startswith('# ') or \
                token_str.startswith('#!') or \
                all(c == '#' for c in token_str):
                continue
            self.add_message(self.MSG, line=token.start[0], col_offset=token.start[1])


def register(linter: PyLinter) -> None:
    linter.register_checker(CommentSpaceChecker(linter))
