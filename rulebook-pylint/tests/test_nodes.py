from unittest import TestCase, main

from astroid import extract_node

from rulebook_pylint.nodes import get_assignname, has_decorator, has_jump_statement, \
    is_multiline


class TestNodes(TestCase):
    def test_get_assignname(self):
        node1 = \
            extract_node(
                '''
                foo = 0  #@
                ''',
            )
        self.assertEqual(get_assignname(node1).name, 'foo')

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
        self.assertTrue(has_decorator(node1, 'decorator'))
        self.assertTrue(has_decorator(node2, 'decorator'))

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
        self.assertTrue(has_jump_statement(node1))
        self.assertTrue(has_jump_statement(node2))

    def test_is_multiline(self):
        node1, node2, node3, node4, node5, node6 = \
            extract_node(
                '''
                foo = 0  #@

                if 1 == 2:
                    print()  #@
                if 1 == 2:  #@
                    print()
                    print()

                match bar:
                    case 0:  #@
                        print()
                    case 1:  #@
                        print()
                        print()

                def foo(bar):  #@
                    print()
                ''',
            )
        self.assertFalse(is_multiline(node1))
        self.assertFalse(is_multiline(node2))
        self.assertTrue(is_multiline(node3))
        self.assertFalse(is_multiline(node4))
        self.assertTrue(is_multiline(node5))
        self.assertTrue(is_multiline(node6))


if __name__ == '__main__':
    main()
