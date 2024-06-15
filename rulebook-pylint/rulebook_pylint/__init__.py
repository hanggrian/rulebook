"""main"""

from typing import TYPE_CHECKING

from pylint.utils import register_plugins

if TYPE_CHECKING:
    from pylint.lint import PyLinter


def initialize(linter: 'PyLinter') -> None:
    register_plugins(linter, __path__[0])
