from collections.abc import Callable

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


def has_jump_statement(start: Token, end: Token) -> bool:
    sibling: Token | None = \
        next_sibling(
            start,
            lambda t: t is end or t.str in {'return', 'break', 'continue', 'throw', 'goto'},
        )
    if sibling is None:
        return False
    return sibling.str != end.str


def is_multiline(start: Token, end: Token) -> bool:
    sibling: Token | None = next_sibling(start, lambda t: t is end or t.linenr > start.linenr)
    if sibling is None:
        return False
    return sibling.linenr > start.linenr


def parent(token: Token, predicate: Callable[[Token], bool]) -> Token | None:
    while token is not None:
        if predicate(token):
            return token
        token = token.astParent
    return None


def prev_sibling(token: Token, predicate: Callable[[Token], bool]) -> Token | None:
    while token is not None:
        if predicate(token):
            return token
        token = token.previous
    return None


def next_sibling(token: Token, predicate: Callable[[Token], bool]) -> Token | None:
    while token is not None:
        if predicate(token):
            return token
        token = token.next
    return None
