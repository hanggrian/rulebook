from unittest import TestCase, main

from rulebook_cppcheck.messages import Messages


class TestMessages(TestCase):
    def test_get(self):
        self.assertEqual(
            Messages.get('inner.class.position'),
            'Move inner class to the bottom.',
        )
        self.assertEqual(
            Messages.get('file.size', 99),
            "Reduce file size to '99'.",
        )


if __name__ == '__main__':
    main()
