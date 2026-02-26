from __future__ import annotations

from astroid.nodes import ImportFrom
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class NamedImportOrderChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#named-import-order"""
    _MSG: str = 'named.import.order'

    name: str = 'named-import-order'
    msgs: dict[str, tuple[str, str, str]] = _Messages.of(_MSG)

    def visit_importfrom(self, node: ImportFrom) -> None:
        last_name: str | None = None
        for name in node.names:
            import_name: str = name[0]

            # checks for violation
            if last_name is not None and \
                last_name > import_name:
                self.add_message(
                    self._MSG,
                    node=node,
                    args=(import_name, last_name),
                )

            last_name = import_name


def register(linter: PyLinter) -> None:
    linter.register_checker(NamedImportOrderChecker(linter))
