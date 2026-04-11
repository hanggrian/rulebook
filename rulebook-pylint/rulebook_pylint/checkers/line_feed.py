from __future__ import annotations

from astroid.nodes import Module
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_pylint.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class LineFeedChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#line-feed"""
    _MSG: str = 'line.feed'

    name: str = 'line-feed'
    msgs: dict[str, tuple[str, str, str]] = Messages.of(_MSG)

    def process_module(self, node: Module) -> None:
        # checks for violation
        with node.stream() as stream:
            text: str = stream.read().decode('UTF-8')
            if not text.endswith('\r\n') and \
                not text.endswith('\r'):
                return
            self.add_message(self._MSG, line=len(stream.readlines()))


def register(linter: PyLinter) -> None:
    linter.register_checker(LineFeedChecker(linter))
