from tokenize import TokenInfo, COMMENT

from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages
from rulebook_pylint.nodes import _is_comment_empty

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class DuplicateBlankLineInCommentChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line-in-comment"""
    MSG: str = 'duplicate.blank.line.in.comment'

    name: str = 'duplicate-blank-line-in-comment'
    msgs: dict[str, MessageDefinitionTuple] = _Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        last_empty_token: TokenInfo | None = None
        # checks for violation
        for token in [t for t in tokens if t.type == COMMENT and _is_comment_empty(t)]:
            if not last_empty_token:
                last_empty_token = token
                continue
            if last_empty_token.start[0] + 1 == token.start[0]:
                self.add_message(self.MSG, line=token.start[0], col_offset=token.start[1])

            # keep previous token for comparison
            last_empty_token = token


def register(linter: 'PyLinter') -> None:
    linter.register_checker(DuplicateBlankLineInCommentChecker(linter))
