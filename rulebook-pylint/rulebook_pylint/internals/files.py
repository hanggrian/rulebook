from astroid import NodeNG


def get_fromlineno_after(lines: list[str], current: NodeNG, previous: NodeNG) -> int:
    index = current.fromlineno - 1
    while index > 0:
        if lines[index] == b'' or index == previous.tolineno - 1:
            return index + 1
        index -= 1
    return current.fromlineno - 1
