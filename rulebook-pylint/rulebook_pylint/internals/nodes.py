from tokenize import TokenInfo, COMMENT

from astroid import NodeNG, Name, Assign, AnnAssign, AssignName, FunctionDef, ClassDef, If, \
    MatchCase, Return, Raise


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
        decorator: NodeNG
        for decorator in node.decorators.nodes:
            if isinstance(decorator, Name) and decorator.name == name:
                return True
    return False


def has_return_or_raise(node: NodeNG) -> bool:
    body: list[NodeNG]
    if isinstance(node, If):
        body = node.body
    elif isinstance(node, MatchCase):
        body = node.body
    else:
        return False
    return any(isinstance(node, (Return, Raise)) for node in body)


def is_multiline(node: NodeNG) -> bool:
    return node.end_lineno > node.lineno


def is_comment_empty(token: TokenInfo) -> bool:
    return token.type == COMMENT and token.string == '#'
