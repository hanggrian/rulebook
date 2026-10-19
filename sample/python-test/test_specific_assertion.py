from unittest import TestCase


class TestConfusingAssertion(TestCase):
    def test(self):
        actual = 1
        expected = 2
        self.assertEqual(actual, expected)
        self.assertIs(actual, expected)

        b = True
        self.assertTrue(b)

        s = 'Hello'
        self.assertIsNone(s)
