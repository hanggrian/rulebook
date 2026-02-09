from unittest import TestCase, main

from astroid import extract_node

from rulebook_pylint.nodes import _get_assignname, _has_decorator, _has_jump_statement, \
    _is_multiline


class TestNodes(TestCase):
    def test_get_assignname(self):
        node1 = \
            extract_node(
                '''
                foo = 0  #@
                ''',
            )
        self.assertEqual(_get_assignname(node1).name, 'foo')

    def test_has_decorator(self):
        node1, node2 = \
            extract_node(
                '''
                @decorator
                class Foo:  #@
                    pass

                @decorator
                def bar():  #@
                    pass
                ''',
            )
        self.assertTrue(_has_decorator(node1, 'decorator'))
        self.assertTrue(_has_decorator(node2, 'decorator'))

    def test_has_jump_statement(self):
        node1, node2 = \
            extract_node(
                '''
                def foo(bar):
                    if True:  #@
                        return
                    for i in []:
                        match bar:
                            case True:  #@
                                continue
                ''',
            )
        self.assertTrue(_has_jump_statement(node1))
        self.assertTrue(_has_jump_statement(node2))

    def test_is_multiline(self):
        node1, node2 = \
            extract_node(
                '''
                foo = 0  #@


                def foo(bar):  #@
                    print()
                ''',
            )
        self.assertFalse(_is_multiline(node1))
        self.assertTrue(_is_multiline(node2))


if __name__ == '__main__':
    main()
