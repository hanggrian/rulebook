from typing import Any
from unittest import TestCase


class CheckerTestCase(TestCase):
    CHECKER_CLASS: Any

    def setUp(self) -> None:
        self.checker = self.CHECKER_CLASS()
