from astroid import Module
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookFileChecker
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class DuplicateBlankLineChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#duplicate-blank-line"""
    MSG: str = 'duplicate-blank-line'

    name: str = 'duplicate-blank-line'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def process_module(self, node: Module) -> None:
        # checks for violation
        counter: int = 0
        with node.stream() as stream:
            for (i, line) in enumerate(stream.readlines()):
                counter = counter + 1 if not line.strip() else 0
                if counter < 3:
                    continue
                self.add_message(self.MSG, line=i + 1)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(DuplicateBlankLineChecker(linter))
