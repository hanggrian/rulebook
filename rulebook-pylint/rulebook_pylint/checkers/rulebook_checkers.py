from abc import ABC

from astroid import Module
from pylint.checkers import BaseChecker, BaseTokenChecker, BaseRawFileChecker


class RulebookChecker(BaseChecker):
    """Standard checker with `visit_` prefix functions."""


class RulebookTokenChecker(BaseTokenChecker, ABC):
    """Override `process_tokens` to iterate each token."""


class RulebookFileChecker(BaseRawFileChecker, ABC):
    """Override `process_module` to capture file at once."""
    lines: list[bytes]

    def process_module(self, node: Module) -> None:
        with node.stream() as stream:
            self.lines = [s.strip() for s in stream.readlines()]
