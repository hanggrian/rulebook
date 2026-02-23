from unittest import main

from astroid import extract_node
from pylint.testutils import CheckerTestCase

from rulebook_pylint.checkers.lowercase_hex import LowercaseHexChecker
from ..tests import assert_properties, msg


# noinspection PyTypeChecker
class TestLowercaseHexChecker(CheckerTestCase):
    CHECKER_CLASS = LowercaseHexChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    def test_lowercase_hexadecimal_letters(self):
        node1, node2 = \
            extract_node(
                '''
                foo = 0x00bb00  #@

                print(0xaa00cc)  #@
                ''',
            )
        with self.assertNoMessages():
            self.checker.visit_const(node1.value)
            self.checker.visit_const(node2.args[0])

    def test_uppercase_hexadecimal_letters(self):
        node1, node2 = \
            extract_node(
                '''
                foo = 0X00BB00  #@

                print(0XAA00CC)  #@
                ''',
            )
        with self.assertAddsMessages(
            msg(LowercaseHexChecker._MSG, (2, 6, 2, 14), node1.value, '0x00bb00'),
            msg(LowercaseHexChecker._MSG, (4, 6, 4, 14), node2.args[0], '0xaa00cc'),
        ):
            self.checker.visit_const(node1.value)
            self.checker.visit_const(node2.args[0])


if __name__ == '__main__':
    main()
