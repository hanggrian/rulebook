from unittest import main

from astroid import parse, extract_node
from pylint.testutils import CheckerTestCase
from rulebook_pylint.trailing_comma_in_call import TrailingCommaInCallChecker

from .tests import assert_properties, msg


class TestTrailingCommaInCallChecker(CheckerTestCase):
    CHECKER_CLASS = TrailingCommaInCallChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_single_line_parameter_without_trailing_comma(self):
        s = \
            '''
            def foo():
                print(1, 2)  #@
            '''
        node_all = parse(s)
        node1 = extract_node(s)
        with self.assertNoMessages():
            self.checker.process_module(node_all)
            self.checker.visit_call(node1)

    def test_single_line_parameter_with_trailing_comma(self):
        s = \
            '''
            def foo():
                print(1, 2,)  #@
            '''
        node_all = parse(s)
        node1 = extract_node(s)
        with self.assertAddsMessages(msg(TrailingCommaInCallChecker.MSG_SINGLE, (3, 14, 15))):
            self.checker.process_module(node_all)
            self.checker.visit_call(node1)

    def test_multiline_parameter_without_trailing_comma(self):
        s = \
            '''
            def foo():
                print(  #@
                    1,
                    2
                )
            '''
        node_all = parse(s)
        node1 = extract_node(s)
        with self.assertAddsMessages(msg(TrailingCommaInCallChecker.MSG_MULTI, (5, 8, 9))):
            self.checker.process_module(node_all)
            self.checker.visit_call(node1)

    def test_multiline_parameter_with_trailing_comma(self):
        s = \
            '''
            def foo():
                print(  #@
                    1,
                    2,
                )
            '''
        node_all = parse(s)
        node1 = extract_node(s)
        with self.assertNoMessages():
            self.checker.process_module(node_all)
            self.checker.visit_call(node1)

    def test_skip_inline_comment(self):
        s = \
            '''
            def foo():
                print(  #@
                    1,  # 1
                    2,  # 2
                )
            '''
        node_all = parse(s)
        node1 = extract_node(s)
        with self.assertNoMessages():
            self.checker.process_module(node_all)
            self.checker.visit_call(node1)

    def test_aware_of_chained_single_line_calls(self):
        s = \
            '''
            def foo():
                bar(1) \
                    .bar(2) \
                    .bar(  #@
                        3,
                    )
            '''
        node_all = parse(s)
        node1 = extract_node(s)
        with self.assertNoMessages():
            self.checker.process_module(node_all)
            self.checker.visit_call(node1)


if __name__ == '__main__':
    main()
