from unittest import TestCase, main

from astroid import parse, extract_node

from rulebook_pylint.files import _get_fromlineno_before, _has_comment_above


class TestFiles(TestCase):
    def test_get_fromlineno_before(self):
        code = \
            '''
            foo = 0  #@

            bar = 1  #@
            '''
        (node1, node2) = extract_node(code)
        lines = self._parse(code)
        self.assertEqual(_get_fromlineno_before(lines, node2, node1), 3)

    def test_has_comment_above(self):
        code = \
            '''
            def foo(bar):
                match bar:
                    case 0:  #@
                        pass
                    # comment
                    case 1:  #@
                        pass
            '''
        (node1, node2) = extract_node(code)
        lines = self._parse(code)
        self.assertFalse(_has_comment_above(lines, node1))
        self.assertTrue(_has_comment_above(lines, node2))

    @staticmethod
    def _parse(s):
        with parse(s).stream() as stream:
            return [s.strip() for s in stream.readlines()]


if __name__ == '__main__':
    main()
