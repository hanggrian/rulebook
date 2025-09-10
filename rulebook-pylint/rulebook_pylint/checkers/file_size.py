from astroid import Module
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple, Options
from rulebook_pylint.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_pylint.messages import _Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class FileSizeChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#file-size"""
    MSG: str = 'file-size'

    name: str = 'file-size'
    msgs: dict[str, MessageDefinitionTuple] = _Messages.of(MSG)
    options: Options = (
        (
            'rulebook-max-file-size',
            {
                'default': 1000,
                'type': 'int',
                'metavar': '<int>',
                'help': 'Max lines of code that is allowed.',
            },
        ),
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
            self.add_message(self.MSG, line=0, args=self._max_file_size)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(FileSizeChecker(linter))
