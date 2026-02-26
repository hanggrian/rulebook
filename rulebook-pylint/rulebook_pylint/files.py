from astroid.nodes import NodeNG


def _get_fromlineno_before(lines: list[bytes], current: NodeNG, previous: NodeNG) -> int:
    """Returns a line number below previous node, above current node."""
    index = current.fromlineno - 1
    while index > 0:
        if lines[index].strip() == b'' or index == previous.tolineno - 1:
            return index + 1
        index -= 1
    return current.fromlineno - 1


def _has_comment_above(lines: list[bytes], current: NodeNG) -> bool:
    """Returns true if there is any comment above current node."""
    if lines is None:
        return False
    index = current.fromlineno - 1
    while index > 0:
        line = lines[index].strip()
        if line == b'':
            return False
        if line.startswith(b'#'):
            return True
        index -= 1
    return False
