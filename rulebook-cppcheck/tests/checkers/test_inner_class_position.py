from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers.inner_class_position import InnerClassPositionChecker
from rulebook_cppcheck.messages import _Messages
from ..tests import assert_properties, CheckerTestCase


class TestInnerClassPositionChecker(CheckerTestCase):
    CHECKER_CLASS = InnerClassPositionChecker

    def test_rule_properties(self):
        assert_properties(self.CHECKER_CLASS)

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_inner_classes_at_the_bottom(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                class Foo {
                    int bar = 0;
                    void baz() {}
                    class Inner {}
                    class AnotherInner {}
                };
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_inner_classes_before_members(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                class Foo {
                    class Inner {}
                    int bar = 0;
                    class AnotherInner {}
                    void baz() {}
                };
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == 'bar'),
            _Messages.get(self.checker.MSG),
        )

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_skip_anonymous_inner_scope(self, mock_report):
        [
            self.checker.process_token(token) for token in self.dump_tokens(
                '''
                class Foo {
                    class {
                        void bar() {}
                    } anonymous
                };
                ''',
            )
        ]
        mock_report.assert_not_called()

    @patch.object(InnerClassPositionChecker, 'report_error')
    def test_target_all_class_like_declarations(self, mock_report):
        tokens = \
            self.dump_tokens(
                '''
                struct OuterStruct {
                    struct InnerStruct {}
                    int member = 0;
                };
                ''',
            )
        [self.checker.process_token(token) for token in tokens]
        mock_report.assert_called_once_with(
            next(t for t in tokens if t.str == 'member' and t.linenr == 4),
            _Messages.get(self.checker.MSG),
        )


if __name__ == '__main__':
    main()
