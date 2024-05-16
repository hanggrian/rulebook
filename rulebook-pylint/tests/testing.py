from astroid import NodeNG
from pylint.checkers import BaseChecker
from pylint.testutils import MessageTest


def msg(checker: BaseChecker, node: NodeNG, location: tuple[int, int], args: str | None = None):
    return MessageTest(
        msg_id=checker.name,
        node=node,
        line=location[0],
        col_offset=location[1],
        args=args,
    )
