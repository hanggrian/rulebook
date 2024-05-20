from typing import TYPE_CHECKING

from astroid import NodeNG, Assign, AssignAttr, AssignName, AnnAssign, AugAssign, Tuple
from pylint.checkers import BaseChecker
from pylint.typing import MessageDefinitionTuple, Options

from .internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class PropertyIdiomaticNamingChecker(BaseChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#property-idiomatic-naming
    """
    MSG: str = 'property.idiomatic.naming'

    name: str = 'property-idiomatic-naming'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG)
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
            return None

        # checks for violation
        target: NodeNG
        if isinstance(node, AnnAssign):
            target = node.target
            self._process(target)
            return None
        for target in node.targets:
            if not isinstance(target, Tuple):
                self._process(target)
                continue
            for elt in target.elts:
                self._process(elt)

    def _process(self, node: NodeNG) -> None:
        if isinstance(node, AssignAttr):
            name: str = node.attrname
        elif isinstance(node, AssignName):
            name: str = node.name
        else:
            return None

        if name not in self.linter.config.rulebook_prohibited_properties:
            return None
        self.add_message(PropertyIdiomaticNamingChecker.MSG, node=node)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(PropertyIdiomaticNamingChecker(linter))
