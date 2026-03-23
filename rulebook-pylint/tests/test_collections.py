from unittest import TestCase, main

from rulebook_pylint.collections import _two_way_dict


class TestNodes(TestCase):
    def test_two_way_dict(self):
        dictionary = _two_way_dict(['a', 'b'])
        self.assertEqual(dictionary['a'], 'b')
        self.assertEqual(dictionary['b'], 'a')


if __name__ == '__main__':
    main()
