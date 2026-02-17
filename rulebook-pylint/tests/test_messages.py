from unittest import TestCase, main

from rulebook_pylint.messages import _Messages


class TestMessages(TestCase):
    def test_get(self):
        self.assertEqual(
            _Messages.get('inner.class.position'),
            'Move inner class to the bottom.',
        )

    def test_of(self):
        msgs = _Messages.of('inner.class.position')
        msg = msgs.get('E6143', None)
        # ids are assigned differently with CLI
        if not msg:
            return
        self.assertEqual(msg[0], 'Move inner class to the bottom.')


if __name__ == '__main__':
    main()
