from astroid import Module
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple, Options
from rulebook_pylint.checkers import RulebookRawChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class FileSizeLimitationChecker(RulebookRawChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#file-size-limitation"""
    MSG: str = 'file-size-limitation'

    name: str = 'file-size-limitation'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)
    options: Options = \
        (
            (
                'rulebook-limit-file-size',
                {
                    'default': 1000,
                    'type': 'int',
                    'metavar': '<int>',
                    'help': 'Max lines of code that is allowed.',
                },
            ),
        )

    _limit_file_size: int

    def open(self) -> None:
        self._limit_file_size = self.linter.config.rulebook_limit_file_size

    def process_module(self, node: Module) -> None:
        # checks for violation
        with node.stream() as stream:
            size: int = len(stream.readlines())
            if size < self._limit_file_size:
                return
            self.add_message(self.MSG, line=0, args=self._limit_file_size)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(FileSizeLimitationChecker(linter))
