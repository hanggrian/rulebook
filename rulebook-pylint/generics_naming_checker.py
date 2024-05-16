from typing import TYPE_CHECKING

from astroid import NodeNG, Assign, Call
from messages import Messages
from pylint.checkers import BaseChecker
from pylint.typing import MessageDefinitionTuple, Options

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class GenericsNamingChecker(BaseChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#generics-naming
    """
    name: str = 'generics-naming'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(name)
    options: Options = (
        (
            'rulebook-common-generics',
            {
                'default': ('E', 'K', 'N', 'T', 'V'),
                'type': 'csv',
                'metavar': '<comma-separated names>',
                'help': 'A set of common generics.',
            },
        ),
    )

    def visit_assign(self, node: Assign) -> None:
        # only target TypeVar declaration
        call: Call = node.value
        if not isinstance(call, Call) or call.func.name != 'TypeVar':
            return

        # checks for violation
        identifier: NodeNG = call.args[0]
        if identifier.value in self.linter.config.rulebook_common_generics:
            return
        self.add_message(GenericsNamingChecker.name, node=node, args=identifier.value)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(GenericsNamingChecker(linter))
