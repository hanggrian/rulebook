from unittest import main

from astroid import parse
from pylint.testutils import CheckerTestCase
from rulebook_pylint.trailing_comma_in_declaration import TrailingCommaInDeclarationChecker

from .tests import assert_properties, msg


class TestTrailingCommaInDeclarationChecker(CheckerTestCase):
    CHECKER_CLASS = TrailingCommaInDeclarationChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_single_line_parameter_without_trailing_comma(self):
        s = \
            '''
            class Foo(a, b):
                pass

            def bar(a, b):
                pass
            '''
        node_all = parse(s)
        with self.assertNoMessages():
            self.checker.process_module(node_all)
            self.checker.visit_classdef(node_all.body[0])
            self.checker.visit_functiondef(node_all.body[1])

    def test_single_line_parameter_with_trailing_comma(self):
        s = \
            '''
            class Foo(a, b,):
                pass

            def bar(a, b,):
                pass
            '''
        node_all = parse(s)
        with self.assertAddsMessages(
            msg(TrailingCommaInDeclarationChecker.MSG_SINGLE, (2, 14, 15)),
            msg(TrailingCommaInDeclarationChecker.MSG_SINGLE, (5, 12, 13)),
        ):
            self.checker.process_module(node_all)
            self.checker.visit_classdef(node_all.body[0])
            self.checker.visit_functiondef(node_all.body[1])

    def test_multiline_parameter_without_trailing_comma(self):
        s = \
            '''
            class Foo(
                a,
                b
            ):
                pass

            def bar(
                a,
                b
            ):
                pass
            '''
        node_all = parse(s)
        with self.assertAddsMessages(
            msg(TrailingCommaInDeclarationChecker.MSG_MULTI, (4, 4, 5)),
            msg(TrailingCommaInDeclarationChecker.MSG_MULTI, (10, 4, 5)),
        ):
            self.checker.process_module(node_all)
            self.checker.visit_classdef(node_all.body[0])
            self.checker.visit_functiondef(node_all.body[1])

    def test_multiline_parameter_with_trailing_comma(self):
        s = \
            '''
            class Foo(
                a,
                b,
            ):
                pass

            def bar(
                a,
                b,
            ):
                pass
            '''
        node_all = parse(s)
        with self.assertNoMessages():
            self.checker.process_module(node_all)
            self.checker.visit_classdef(node_all.body[0])
            self.checker.visit_functiondef(node_all.body[1])

    def test_skip_inline_comment(self):
        s = \
            '''
            class Foo(
                a,  # 1
                b,  # 1
            ):
                pass

            def bar(
                a,  # 1
                b,  # 1
            ):
                pass
            '''
        node_all = parse(s)
        with self.assertNoMessages():
            self.checker.process_module(node_all)
            self.checker.visit_classdef(node_all.body[0])
            self.checker.visit_functiondef(node_all.body[1])


if __name__ == '__main__':
    main()
