from __future__ import annotations

from tokenize import ENCODING, NL, TokenInfo

from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class UnnecessaryLeadingBlankLineChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#unnecessary-leading-blank-line"""
    _MSG: str = 'unnecessary.leading.blank.line'

    name: str = 'unnecessary-leading-blank-line'
    msgs: dict[str, tuple[str, str, str]] = Messages.of(_MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        for token in [t for t in tokens if t.type != ENCODING]:
            # checks for violation
            if token.type == NL:
                self.add_message(self._MSG, line=1)
            return


def register(linter: PyLinter) -> None:
    linter.register_checker(UnnecessaryLeadingBlankLineChecker(linter))
