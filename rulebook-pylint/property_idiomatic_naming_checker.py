from typing import TYPE_CHECKING

from astroid import NodeNG, Assign, AnnAssign, AugAssign, Tuple
from messages import Messages
from pylint.checkers import BaseChecker
from pylint.typing import MessageDefinitionTuple, Options

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class PropertyIdiomaticNamingChecker(BaseChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#property-idiomatic-naming
    """
    name: str = 'property-idiomatic-naming'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(name)
    options: Options = (
        (
            'rulebook-prohibited-properties',
            {
                'default': ('integer', 'string'),
                'type': 'csv',
                'metavar': '<comma-separated names>',
                'help': 'A set of banned words.',
            },
        ),
    )

    def visit_assign(self, node: Assign) -> None:
        # skip assignments with operator
        if isinstance(node, AugAssign):
            return

        # checks for violation
        if isinstance(node, AnnAssign):
            target: NodeNG = node.target
            self.process(target, target.name)
            return
        for target in node.targets:
            if not isinstance(target, Tuple):
                self.process(target, target.name)
                continue
            for elt in target.elts:
                self.process(elt, elt.name)

    def process(self, node: NodeNG, name: str) -> None:
        if name not in self.linter.config.rulebook_prohibited_properties:
            return
        self.add_message(PropertyIdiomaticNamingChecker.name, node=node)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(PropertyIdiomaticNamingChecker(linter))
