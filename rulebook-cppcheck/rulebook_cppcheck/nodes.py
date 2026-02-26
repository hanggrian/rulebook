from collections.abc import Callable

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


def _has_jump_statement(start: Token, end: Token) -> bool:
    return _next_sibling(
        start,
        lambda t: t is end or t.str in {'return', 'break', 'continue', 'throw', 'goto'},
    ).str != end.str


def _is_multiline(start: Token, end: Token) -> bool:
    return _next_sibling(
        start,
        lambda t: t is end or t.linenr > start.linenr,
    ).linenr > start.linenr


def _parent(token: Token, predicate: Callable[[Token], bool]) -> Token | None:
    while token is not None:
        if predicate(token):
            return token
        token = token.astParent
    return None


def _prev_sibling(token: Token, predicate: Callable[[Token], bool]) -> Token | None:
    while token is not None:
        if predicate(token):
            return token
        token = token.previous
    return None


def _next_sibling(token: Token, predicate: Callable[[Token], bool]) -> Token | None:
    while token is not None:
        if predicate(token):
            return token
        token = token.next
    return None
