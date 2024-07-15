from typing import TYPE_CHECKING

from astroid import Module
from pylint.typing import MessageDefinitionTuple, Options
from rulebook_pylint.checkers import RawChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class FileSizeLimitationChecker(RawChecker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#file-size-limitation
    """
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

    def process_module(self, node: Module) -> None:
        # checks for violation
        with node.stream() as stream:
            size: int = len(stream.readlines())
            max_size: int = self.linter.config.rulebook_limit_file_size
            if size < max_size:
                return
            self.add_message(self.MSG, line=0, args=max_size)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(FileSizeLimitationChecker(linter))
