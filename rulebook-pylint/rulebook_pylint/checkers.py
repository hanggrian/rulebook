from abc import ABC

from pylint.checkers import BaseChecker, BaseTokenChecker, BaseRawFileChecker


class Checker(BaseChecker):
    """No custom implementation."""


class TokenChecker(BaseTokenChecker, ABC):
    """No custom implementation."""


class RawChecker(BaseRawFileChecker, ABC):
    """No custom implementation."""
