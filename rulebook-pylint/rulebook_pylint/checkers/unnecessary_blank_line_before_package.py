from tokenize import TokenInfo, ENCODING, NL

from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookTokenChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class UnnecessaryBlankLineBeforePackageChecker(RulebookTokenChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#unnecessary-blank-line-before-package"""
    MSG: str = 'unnecessary.blank.line.before.package'

    name: str = 'unnecessary-blank-line-before-package'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(MSG)

    def process_tokens(self, tokens: list[TokenInfo]) -> None:
        for token in tokens:
            # skip metadata
            type2: int = token.type
            if type2 is ENCODING:
                continue

            # checks for violation
            if type2 is NL:
                self.add_message(self.MSG, line=0)
            return


def register(linter: 'PyLinter') -> None:
    linter.register_checker(UnnecessaryBlankLineBeforePackageChecker(linter))
