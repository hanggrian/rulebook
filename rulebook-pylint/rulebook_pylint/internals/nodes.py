from tokenize import TokenInfo, COMMENT

from astroid import NodeNG, Name, Assign, AnnAssign, AssignName, FunctionDef, ClassDef, If, \
    MatchCase, Return, Raise, Break, Continue


def get_assignname(node: Assign) -> AssignName | None:
    target: NodeNG
    if isinstance(node, AnnAssign):
        target = node.target
    elif isinstance(node.targets[0], AssignName):
        target = node.targets[0]
    else:
        return None

    if isinstance(target, AssignName):
        return target
    return None


def has_decorator(node: FunctionDef | ClassDef, name: str) -> bool:
    if node.decorators:
        if any(isinstance(n, Name) and n.name == name for n in node.decorators.nodes):
            return True
    return False


def has_jump_statement(node: NodeNG) -> bool:
    if not isinstance(node, (If, MatchCase)):
        return False
    return any(isinstance(n, (Return, Raise, Break, Continue)) for n in node.body)


def is_multiline(node: NodeNG) -> bool:
    return node.end_lineno > node.lineno


def is_comment_empty(token: TokenInfo) -> bool:
    return token.type == COMMENT and token.string == '#'
