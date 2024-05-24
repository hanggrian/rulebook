from typing import TYPE_CHECKING

from astroid import Assign, Call, Name, AssignName
from pylint.checkers import BaseChecker
from pylint.typing import MessageDefinitionTuple, Options

from .internals import Messages, get_assignname

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class GenericsNameWhitelistingChecker(BaseChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#generics-name-whitelisting
    """
    MSG: str = 'generics-name-whitelisting'

    name: str = 'generics-name-whitelisting'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)
    options: Options = (
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
            return None
        call: Call = node.value
        if not isinstance(call.func, Name) or call.func.name != 'TypeVar':
            return None

        # get assigned property
        target: AssignName | None = get_assignname(node)
        if not target:
            return None

        # checks for violation
        if target.name in self.linter.config.rulebook_whitelist_generics_names:
            return None
        self.add_message(GenericsNameWhitelistingChecker.MSG, node=target, args=target.name)
        return None

def register(linter: 'PyLinter') -> None:
    linter.register_checker(GenericsNameWhitelistingChecker(linter))
