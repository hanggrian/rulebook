from typing import TYPE_CHECKING

from astroid import Assign, Call, Name, AssignName
from pylint.checkers import BaseChecker
from pylint.typing import MessageDefinitionTuple, Options

from .internals import Messages, get_assignname

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class GenericsCommonNamingChecker(BaseChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#generics-common-naming
    """
    MSG: str = 'generics-common-naming'

    name: str = 'generics-common-naming'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)
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
        if not isinstance(node.value, Call):
            return
        call: Call = node.value
        if not isinstance(call.func, Name) or call.func.name != 'TypeVar':
            return

        # get assigned property
        target: AssignName | None = get_assignname(node)
        if not target:
            return

        # checks for violation
        if target.name in self.linter.config.rulebook_common_generics:
            return
        self.add_message(GenericsCommonNamingChecker.MSG, node=target, args=target.name)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(GenericsCommonNamingChecker(linter))
