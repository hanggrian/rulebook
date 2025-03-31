from astroid import Module, ClassDef, Assign, FunctionDef, NodeNG
from pylint.typing import TYPE_CHECKING, MessageDefinitionTuple
from rulebook_pylint.checkers import RulebookFileChecker
from rulebook_pylint.internals.files import get_fromlineno_after
from rulebook_pylint.internals.messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class MemberSeparatorChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/all/#member-separator"""
    MSG: str = 'member-separator'

    name: str = 'member-separator'
    msgs: dict[str, MessageDefinitionTuple] = Messages.of(MSG)

    def process_module(self, node: Module) -> None:
        # collect source code
        lines: list[str]
        with node.stream() as stream:
            lines = [s.strip() for s in stream.readlines()]

        for class_node in [n for n in node.body if isinstance(n, ClassDef)]:
            self._process(class_node, lines)

    def _process(self, node: NodeNG, lines: list[str]) -> None:
        # recurse
        if not isinstance(node, ClassDef):
            return
        for n in node.body:
            self._process(n, lines)

        # collect members
        members: list[NodeNG] = [n for n in node.body if isinstance(n, (Assign, FunctionDef))]

        for (i, member) in enumerate(members):
            if i == 0:
                continue
            last_member: NodeNG = members[i - 1]

            # single-line fields can be joined
            if isinstance(last_member, Assign) and isinstance(member, Assign):
                continue
            key: str
            last_body: NodeNG
            if isinstance(last_member, FunctionDef):
                key = 'constructor' if last_member.name == '__init__' else 'function'
                last_body = last_member.body[len(last_member.body) - 1]
            else:
                key = 'property'
                last_body = last_member

            # checks for violation
            if last_body.end_lineno != get_fromlineno_after(lines, member, last_body):
                continue
            self.add_message(
                self.MSG,
                args=key,
                line=last_body.lineno,
                end_lineno=last_body.end_lineno,
                col_offset=last_body.col_offset,
                end_col_offset=last_body.end_col_offset,
            )


def register(linter: 'PyLinter') -> None:
    linter.register_checker(MemberSeparatorChecker(linter))
