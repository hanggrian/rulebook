from __future__ import annotations

from astroid.nodes import AssignName, FunctionDef
from pylint.typing import TYPE_CHECKING

from rulebook_pylint.checkers.rulebook_checkers import RulebookChecker

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class MethodParameterNameChecker(RulebookChecker):
    name: str = 'method-parameter-name'
    msgs = {
        'W9904': ("Rename parameter to '%s'.", 'method-parameter-name', 'Rename identifier.'),
    }

    def visit_functiondef(self, node: FunctionDef):
        args: list[AssignName] = node.args.args
        if not args or len(args) < 2:
            return
        if args[0].name != 'self':
            return

        fun_name: str = node.name
        name: str
        if fun_name == 'visit_scope':
            name = 'scope'
        elif fun_name == 'process_tokens':
            name = 'tokens'
        elif fun_name == 'process_module' or fun_name.startswith('visit_'):
            name = 'node'
        else:
            return
        arg = args[1]
        if arg.name == name:
            return
        self.add_message('method-parameter-name', node=arg, args=name)


def register(linter: PyLinter) -> None:
    linter.register_checker(MethodParameterNameChecker(linter))
