from tokenize import TokenInfo, COMMENT

from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages
from rulebook_pylint.nodes import _is_comment_empty

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class CommentSpaceChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#comment-space"""
    MSG: str = 'comment-space'

    name: str = 'comment-space'
    msgs: dict[str, MessageDefinitionTuple] = _Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        # checks for violation
        for token in [t for t in tokens if t.type == COMMENT]:
            if token.string.startswith('# ') or all(c == '#' for c in token.string):
                continue
            self.add_message(self.MSG, line=token.start[0], col_offset=token.start[1])


def register(linter: 'PyLinter') -> None:
    linter.register_checker(CommentSpaceChecker(linter))
