from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers.parameter_wrap import ParameterWrapChecker
from ..tests import assert_properties, msg


# noinspection PyTypeChecker
class TestParameterWrapChecker(CheckerTestCase):
    CHECKER_CLASS = ParameterWrapChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_single_line_parameters(self):
        node1, node2 = \
            extract_node(
                '''
                def foo(a, b):  #@
                    print()

                def bar():
                    foo('Hello' + 'World', 0)  #@
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(node1)
            self.checker.visit_call(node2)

    def test_multiline_parameters_each_with_newline(self):
        node1, node2 = \
            extract_node(
                '''
                def foo(  #@
                    a,
                    b,
                ):
                    print()

                def bar():
                    foo(  #@
                        'Hello' + \
                            'World',
                        0,
                    )
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(node1)
            self.checker.visit_call(node2)

    def test_multiline_parameters_each_without_newline(self):
        node1, node2 = \
            extract_node(
                '''
                def foo(  #@
                    a, b,
                    c,
                ):
                    print()

                def bar():
                    foo(  #@
                        0, 1,
                        2,
                    )
                ''',
            )
        with self.assertAddsMessages(
            msg(ParameterWrapChecker.MSG, (3, 7, 8), node1.args.args[1]),
            msg(ParameterWrapChecker.MSG, (10, 11, 12), node2.args[1]),
        ):
            self.checker.visit_functiondef(node1)
            self.checker.visit_call(node2)

    def test_aware_of_chained_single_line_calls(self):
        node1, node2 = \
            extract_node(
                '''
                def foo():  #@
                    bar() \
                        .bar() \
                        .bar()  #@
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(node1)
            self.checker.visit_call(node2)

    def test_allow_comments_between_parameters(self):
        node1 = \
            extract_node(
                '''
                def foo(  #@
                    a,
                    # Comment
                    b,
                ):
                    pass
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(node1)

    def test_allow_sam(self):
        node1 = \
            extract_node(
                '''
                def foo():  #@
                    bar(
                        lambda x: x +
                            2,
                    )
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_functiondef(node1)


if __name__ == '__main__':
    main()
