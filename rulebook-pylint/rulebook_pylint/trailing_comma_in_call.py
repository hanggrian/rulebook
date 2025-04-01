from astroid import Call, NodeNG
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookFileChecker
from rulebook_pylint.internals.files import strip_comment
from rulebook_pylint.internals.messages import Messages
from rulebook_pylint.internals.nodes import is_multiline

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class TrailingCommaInCallChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#trailing-comma-in-call"""
    MSG_SINGLE: str = 'trailing-comma-in-call-single'
    MSG_MULTI: str = 'trailing-comma-in-call-multi'

    name: str = 'trailing-comma-in-call'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG_SINGLE, MSG_MULTI)

    def visit_call(self, node: Call) -> None:
        # find last parameter
        if not node.args:
            return
        arg: NodeNG = [n for n in node.args if n][-1]

        # checks for violation
        if not is_multiline(node):
            line: bytes = strip_comment(self.lines, arg)
            if not line.endswith(b')'):
                return
            line = line.split(b')', 1)[0].rstrip()
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
        # skip single multiline arg only in call
        if len(node.args) == 1:
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
    linter.register_checker(TrailingCommaInCallChecker(linter))
