from typing import TYPE_CHECKING

from astroid import Assign, Call, Name, AssignName
from pylint.typing import MessageDefinitionTuple, Options
from rulebook_pylint.checkers import Checker
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import get_assignname

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class GenericsNameWhitelistingChecker(Checker):
    """See wiki: https://github.com/hanggrian/rulebook/wiki/Rules/#generics-name-whitelisting
    """
    MSG: str = 'generics-name-whitelisting'

    name: str = 'generics-name-whitelisting'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)
    options: Options = \
        (
            (
                'rulebook-whitelist-generics-names',
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
        if target.name in self.linter.config.rulebook_whitelist_generics_names:
            return
        self.add_message(self.MSG, node=target, args=target.name)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(GenericsNameWhitelistingChecker(linter))
