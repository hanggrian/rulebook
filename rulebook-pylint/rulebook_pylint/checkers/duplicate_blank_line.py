from __future__ import annotations

from astroid.nodes import Module
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class DuplicateBlankLineChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line"""
    _MSG: str = 'duplicate.blank.line'

    name: str = 'duplicate-blank-line'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)

    def process_module(self, node: Module) -> None:
        # checks for violation
        counter: int = 0
        with node.stream() as stream:
            for (i, line) in enumerate(stream.readlines()):
                counter = counter + 1 if not line.strip() else 0
                if counter < 3:
                    continue
                self.add_message(self._MSG, line=i + 1)


def register(linter: PyLinter) -> None:
    linter.register_checker(DuplicateBlankLineChecker(linter))
