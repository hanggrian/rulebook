from collections.abc import Callable

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


def _prev_sibling(token: Token, predicate: Callable[[Token], bool]) -> Token | None:
    while token:
        if predicate(token):
            return token
        token = token.previous
    return None


def _next_sibling(token: Token, predicate: Callable[[Token], bool]) -> Token | None:
    while token:
        if predicate(token):
            return token
        token = token.next
    return None
