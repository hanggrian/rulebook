from astroid import NodeNG


def _get_fromlineno_before(lines: list[bytes], current: NodeNG, previous: NodeNG) -> int:
    index = current.fromlineno - 1
    while index > 0:
        if lines[index].strip() == b'' or index == previous.tolineno - 1:
            return index + 1
        index -= 1
    return current.fromlineno - 1


def _has_comment_above(lines: list[bytes], current: NodeNG) -> bool:
    index = current.fromlineno - 1
    while index > 0:
        line = lines[index].strip()
        if line == b'':
            return False
        if line.startswith(b'#') or \
            line.startswith(b'"""') or \
            line.startswith(b"'''"):
            return True
        index -= 1
    return False


def _strip_comment(lines: list[bytes], current: NodeNG) -> bytes:
    return lines[current.lineno - 1].split(b'#', 1)[0].rstrip()
