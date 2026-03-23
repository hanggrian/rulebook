from unittest import TestCase


class TestConfusingAssertion(TestCase):
    def test(self):
        condition = True
        self.assertTrue(condition)
