from __future__ import annotations

from tokenize import ENCODING, NL, TokenInfo

from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class UnnecessaryInitialBlankLineChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#unnecessary-initial-blank-line"""
    _MSG: str = 'unnecessary.initial.blank.line'

    name: str = 'unnecessary-initial-blank-line'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        for token in tokens:
            # skip metadata
            type2: int = token.type
            if type2 is ENCODING:
                continue

            # checks for violation
            if type2 is NL:
                self.add_message(self._MSG, line=0)
            return


def register(linter: PyLinter) -> None:
    linter.register_checker(UnnecessaryInitialBlankLineChecker(linter))
