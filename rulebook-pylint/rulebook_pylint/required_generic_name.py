from astroid import Assign, Call, Name, AssignName
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple, Options
from rulebook_pylint.checkers import RulebookChecker
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import get_assignname

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class RequiredGenericNameChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/all/#required-generic-name"""
    MSG: str = 'required-generic-name'

    name: str = 'required-generic-name'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)
    options: Options = (
        (
            'rulebook-allow-generics-names',
            {
                'default': ('E', 'K', 'N', 'T', 'V'),
                'type': 'csv',
                'metavar': '<comma-separated names>',
                'help': 'A set of common generics.',
            },
        ),
    )

    _allow_generics_names: list[str]

    def open(self) -> None:
        self._allow_generics_names = self.linter.config.rulebook_allow_generics_names

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
        if target.name in self._allow_generics_names:
            return
        self.add_message(
            self.MSG,
            node=target,
            args=', '.join(self._allow_generics_names),
        )


def register(linter: 'PyLinter') -> None:
    linter.register_checker(RequiredGenericNameChecker(linter))
