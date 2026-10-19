from __future__ import annotations

from astroid.nodes import Import, ImportFrom, Module, NodeNG
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_pylint.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class NoDuplicateImportChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#no-duplicate-import"""
    _MSG: str = 'no.duplicate.import'

    name: str = 'no-duplicate-import'
    msgs: dict[str, tuple[str, str, str]] = Messages.of(_MSG)

    _import_names: set[str]

    def process_module(self, node: Module) -> None:
        self._import_names = set()
        super().process_module(node)

        in_import: bool = False
        for _, line in enumerate(self.lines, start=1):
            # collect import statements
            if isinstance(line, bytes):
                line = line.decode('UTF-8', errors='replace')
            if not in_import:
                stripped: str = line.strip()
                if not stripped.startswith('import ') and not stripped.startswith('from '):
                    continue

            in_import = False

    def visit_import(self, node: Import) -> None:
        for name, _ in node.names:
            self._process(name, node)

    def visit_importfrom(self, node: ImportFrom) -> None:
        if not node.modname:
            return
        self._process(node.modname, node)

    def _process(self, name: str, node: NodeNG) -> None:
        if name in self._import_names:
            print(self._import_names)
            self.add_message(
                self._MSG,
                line=node.lineno,
                end_lineno=node.end_lineno,
                col_offset=node.col_offset,
                end_col_offset=node.end_col_offset,
            )
            return
        self._import_names.add(name)


def register(linter: PyLinter) -> None:
    linter.register_checker(NoDuplicateImportChecker(linter))
