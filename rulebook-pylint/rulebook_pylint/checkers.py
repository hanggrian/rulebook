from abc import ABC

from pylint.checkers import BaseChecker, BaseTokenChecker, BaseRawFileChecker


class RulebookChecker(BaseChecker):
    """Standard checker with `visit_` prefix functions."""


class RulebookTokenChecker(BaseTokenChecker, ABC):
    """Override `process_tokens` to iterate each token."""


class RulebookFileChecker(BaseRawFileChecker, ABC):
    """Override `process_module` to capture file at once."""
