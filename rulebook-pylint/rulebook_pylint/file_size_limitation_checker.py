from typing import TYPE_CHECKING

from astroid import Module
from pylint.checkers import BaseRawFileChecker
from pylint.typing import MessageDefinitionTuple, Options

from .internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class FileSizeLimitationChecker(BaseRawFileChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#file-size-limitation
    """
    MSG: str = 'file-size-limitation'

    name: str = 'file-size-limitation'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)
    options: Options = (
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
        with node.stream() as stream:
            size: int = len(stream.readlines())
            max_size: int = self.linter.config.rulebook_limit_file_size
            if size < max_size:
                return None
            self.add_message(FileSizeLimitationChecker.MSG, line=0, args=max_size)
        return None

def register(linter: 'PyLinter') -> None:
    linter.register_checker(FileSizeLimitationChecker(linter))
