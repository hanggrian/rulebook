from __future__ import annotations

from tokenize import TokenInfo, COMMENT, NL

from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages
from rulebook_pylint.nodes import _is_comment_empty

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class CommentTrimChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#comment-trim"""
    _MSG: str = 'comment.trim'

    name: str = 'comment-trim'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        for i, token in enumerate(tokens):
            # target comment
            if token.type is not COMMENT:
                continue

            # continue if this comment is first line
            if i - 2 >= 0 and \
                tokens[i - 1].type is NL and \
                tokens[i - 2].type is COMMENT:
                return

            # iterate to find last
            j: int = i
            while j + 2 < len(tokens) and \
                tokens[j + 1].type is NL and \
                tokens[j + 2].type is COMMENT:
                j += 2
            curr_token: TokenInfo = tokens[j]

            # skip blank comment
            if curr_token is token:
                return

            # checks for violation
            if _is_comment_empty(token):
                self.add_message(
                    self._MSG,
                    line=token.start[0],
                    col_offset=token.start[1],
                )
            if _is_comment_empty(curr_token):
                self.add_message(
                    self._MSG,
                    line=curr_token.start[0],
                    col_offset=curr_token.start[1],
                )
            return


def register(linter: PyLinter) -> None:
    linter.register_checker(CommentTrimChecker(linter))
