from astroid import NodeNG
from pylint.testutils import MessageTest


def msg(
    key: str,
    location: int | tuple[int, int] | tuple[int, int, int] | tuple[int, int, int, int] = 0,
    node: NodeNG | None = None,
    args: object | None = None,
):
    if not isinstance(location, tuple):
        x1: int = location
        y1: None = None
        x2: None = None
        y2: None = None
    elif len(location) == 2:
        x1: int = location[0]
        y1: int = location[1]
        x2: None = None
        y2: None = None
    elif len(location) == 3:
        x1: int = location[0]
        y1: int = location[1]
        x2: int = x1
        y2: int = location[2]
    else:
        x1: int = location[0]
        y1: int = location[1]
        x2: int = location[2]
        y2: int = location[3]
    return MessageTest(
        msg_id=key,
        line=x1,
        col_offset=y1,
        end_line=x2,
        end_col_offset=y2,
        node=node,
        args=args,
    )
