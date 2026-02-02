from collections.abc import Callable

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


def _is_multiline(start_token: Token, end_token: Token) -> bool:
    curr_token: Token | None = start_token
    while curr_token and curr_token is not end_token.next:
        if curr_token.linenr > start_token.linenr:
            return True
        curr_token = curr_token.next
    return False


def _parent(token: Token, predicate: Callable[[Token], bool]) -> Token | None:
    while token:
        if predicate(token):
            return token
        token = token.astParent
    return None


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


def _has_jump_statement(start: Token, end: Token) -> bool:
    curr_token: Token | None = start
    while curr_token and curr_token is not end:
        if curr_token.str in {'return', 'break', 'continue', 'throw', 'goto'}:
            return True
        curr_token = curr_token.next
    return False
