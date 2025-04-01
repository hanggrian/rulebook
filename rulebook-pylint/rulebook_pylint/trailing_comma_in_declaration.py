from astroid import NodeNG, ClassDef, FunctionDef
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookFileChecker
from rulebook_pylint.internals.files import strip_comment
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class TrailingCommaInDeclarationChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#trailing-comma-in-declaration"""
    MSG_SINGLE: str = 'trailing-comma-in-declaration-single'
    MSG_MULTI: str = 'trailing-comma-in-declaration-multi'

    name: str = 'trailing-comma-in-declaration'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_SINGLE, MSG_MULTI)

    def visit_classdef(self, node: ClassDef) -> None:
        self._process(node.bases)

    def visit_functiondef(self, node: FunctionDef) -> None:
        self._process(node.args.args)

    def _process(self, args: list[NodeNG]) -> None:
        # find last parameter
        if not args:
            return
        arg: NodeNG = [n for n in args if n][-1]

        # checks for violation
        if not args[-1].end_lineno > args[0].lineno:
            line: bytes = strip_comment(self.lines, arg)
            if not line.endswith(b'):'):
                return
            line = line.split(b'):', 1)[0].rstrip()
            if not line.endswith(b','):
                return
            self.add_message(
                self.MSG_SINGLE,
                line=arg.lineno,
                end_lineno=arg.end_lineno,
                col_offset=arg.end_col_offset,
                end_col_offset=arg.end_col_offset + 1,
            )
            return
        if strip_comment(self.lines, arg).endswith(b','):
            return
        self.add_message(
            self.MSG_MULTI,
            line=arg.end_lineno,
            end_lineno=arg.end_lineno,
            col_offset=arg.col_offset,
            end_col_offset=arg.end_col_offset,
        )


def register(linter: 'PyLinter') -> None:
    linter.register_checker(TrailingCommaInDeclarationChecker(linter))
