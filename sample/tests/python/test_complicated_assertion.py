from unittest import TestCase


class TestConfusingAssertion(TestCase):
    def test(self):
        self.assertEqual(1, 2)
        self.assertIs(1, 2)

        b = True
        self.assertTrue(b)

        s = 'Hello'
        self.assertIsNone(s)
