from __future__ import annotations

from astroid.nodes import Module
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_pylint.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class ImportGroupingChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#import-grouping"""
    _MSG: str = 'import.grouping'

    name: str = 'import-grouping'
    msgs: dict[str, tuple[str, str, str]] = Messages.of(_MSG)

    def process_module(self, node: Module) -> None:
        super().process_module(node)

        in_import: bool = False
        in_violation: bool = False
        for lineno, line in enumerate(self.lines, start=1):
            # collect import statements
            if isinstance(line, bytes):
                line = line.decode('UTF-8', errors='replace')
            if not in_import:
                stripped: str = line.strip()
                if not stripped.startswith('import ') and not stripped.startswith('from '):
                    continue
                in_import = True
                in_violation = False

            # checks for violation
            if line.rstrip('\r\n').endswith('\\'):
                if not in_violation:
                    self.add_message(self._MSG, line=lineno)
                    in_violation = True
                continue

            in_import = False


def register(linter: PyLinter) -> None:
    linter.register_checker(ImportGroupingChecker(linter))
