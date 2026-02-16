from __future__ import annotations

from astroid.nodes import Module
from pylint.typing import TYPE_CHECKING, Options

from rulebook_pylint.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_pylint.messages import _Messages
from rulebook_pylint.options import MAX_FILE_SIZE_OPTION

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class FileSizeChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#file-size"""
    _MSG: str = 'file.size'

    name: str = 'file-size'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)
    options: Options = (
        MAX_FILE_SIZE_OPTION,
    )

    _max_file_size: int

    def open(self) -> None:
        self._max_file_size = self.linter.config.rulebook_max_file_size

    def process_module(self, node: Module) -> None:
        # checks for violation
        with node.stream() as stream:
            size: int = len(stream.readlines())
            if size < self._max_file_size:
                return
            self.add_message(self._MSG, line=0, args=self._max_file_size)


def register(linter: PyLinter) -> None:
    linter.register_checker(FileSizeChecker(linter))
