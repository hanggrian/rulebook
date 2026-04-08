from unittest import main

from astroid import parse
from pylint.testutils import _tokenize_str

from rulebook_pylint.checkers import \
    BlockCommentClipChecker, \
    BlockCommentTrimChecker, \
    CommentSpacesChecker, \
    DuplicateBlankLineInBlockCommentChecker, \
    NestedIfElseChecker, \
    ParameterWrapChecker, \
    TrailingCommaChecker, \
    UnnecessaryBlankLineAfterColonChecker
from testing.messages import msg
from .all_checkers_case import AllCheckersTestCase
from ..code import get_code


class TestMatplotlib(AllCheckersTestCase):
    def test_matplotlib_pyplot(self):
        code = get_code('pylint', 'matplotlib', 'pyplot.py')
        node_all = parse(code)
        tokens = _tokenize_str(code)
        switch_backend = node_all.body[64]
        _warn_if_gui_out_of_main_thread = node_all.body[65]
        _auto_draw_if_interactive = node_all.body[81]
        delaxes = node_all.body[96]
        sca = node_all.body[97]
        subplot = node_all.body[99]
        last_subplots = node_all.body[103]
        last_subplot_mosaic = node_all.body[107]
        subplot2grid = node_all.body[108]
        xticks = node_all.body[115]
        cla = node_all.body[119]
        clim = node_all.body[122]
        matshow = node_all.body[127]
        with self.assertAddsMessages(
            msg(BlockCommentClipChecker._MSG, (1361, 4, 1363, 7), delaxes.doc_node),
            msg(BlockCommentClipChecker._MSG, (1370, 4, 1372, 7), sca.doc_node),
            msg(BlockCommentClipChecker._MSG, (2494, 4, 2496, 7), cla.doc_node),
            msg(BlockCommentTrimChecker._MSG_LAST, (388, 4, 7), switch_backend.doc_node),
            msg(BlockCommentTrimChecker._MSG_LAST, (1516, 4, 7), subplot.doc_node),
            msg(BlockCommentTrimChecker._MSG_LAST, (1776, 4, 7), last_subplots.doc_node),
            msg(BlockCommentTrimChecker._MSG_LAST, (1949, 4, 7), last_subplot_mosaic.doc_node),
            msg(BlockCommentTrimChecker._MSG_LAST, (2551, 4, 7), clim.doc_node),
            msg(BlockCommentTrimChecker._MSG_LAST, (2668, 4, 7), matshow.doc_node),
            msg(CommentSpacesChecker._MSG, (268, 0)),
            msg(CommentSpacesChecker._MSG, (872, 0)),
            msg(CommentSpacesChecker._MSG, (1256, 0)),
            msg(CommentSpacesChecker._MSG, (1268, 0)),
            msg(CommentSpacesChecker._MSG, (1387, 0)),
            msg(CommentSpacesChecker._MSG, (2089, 0)),
            msg(CommentSpacesChecker._MSG, (2517, 0)),
            msg(CommentSpacesChecker._MSG, (2738, 0)),
            msg(DuplicateBlankLineInBlockCommentChecker._MSG, (2173, 4, 2229, 7), xticks.doc_node),
            # msg(FileSizeChecker.MSG, args=1000),
            msg(
                NestedIfElseChecker._MSG_INVERT,
                (545, 4, 548, 20),
                _warn_if_gui_out_of_main_thread.body[-1],
            ),
            msg(
                NestedIfElseChecker._MSG_INVERT,
                (1086, 4, 1094, 34),
                _auto_draw_if_interactive.body[-1],
            ),
            msg(ParameterWrapChecker._MSG, (1963, 28, 48), subplot2grid.args.args[1]),
            msg(ParameterWrapChecker._MSG, (1964, 22, 34), subplot2grid.args.args[3]),
            msg(TrailingCommaChecker._MSG_MULTI, (166, 30)),
            msg(TrailingCommaChecker._MSG_MULTI, (177, 40)),
            msg(TrailingCommaChecker._MSG_MULTI, (183, 31)),
            msg(TrailingCommaChecker._MSG_MULTI, (203, 85)),
            msg(TrailingCommaChecker._MSG_MULTI, (348, 29)),
            msg(TrailingCommaChecker._MSG_MULTI, (805, 64)),
            msg(TrailingCommaChecker._MSG_MULTI, (889, 12)),
            msg(TrailingCommaChecker._MSG_MULTI, (1130, 35)),
            msg(TrailingCommaChecker._MSG_MULTI, (1273, 12)),
            msg(TrailingCommaChecker._MSG_MULTI, (1527, 65)),
            msg(TrailingCommaChecker._MSG_MULTI, (1583, 12)),
            msg(TrailingCommaChecker._MSG_MULTI, (1600, 12)),
            msg(TrailingCommaChecker._MSG_MULTI, (1617, 12)),
            msg(TrailingCommaChecker._MSG_MULTI, (1631, 12)),
            msg(TrailingCommaChecker._MSG_MULTI, (1797, 17)),
            msg(TrailingCommaChecker._MSG_MULTI, (1813, 17)),
            msg(TrailingCommaChecker._MSG_MULTI, (1829, 17)),
            msg(TrailingCommaChecker._MSG_MULTI, (1846, 17)),
            msg(TrailingCommaChecker._MSG_MULTI, (1966, 12)),
            msg(TrailingCommaChecker._MSG_MULTI, (2171, 12)),
            msg(TrailingCommaChecker._MSG_MULTI, (2257, 12)),
            msg(TrailingCommaChecker._MSG_MULTI, (2342, 12)),
            msg(TrailingCommaChecker._MSG_MULTI, (2420, 12)),
            msg(TrailingCommaChecker._MSG_MULTI, (2525, 12)),
            msg(TrailingCommaChecker._MSG_MULTI, (2613, 71)),
            msg(TrailingCommaChecker._MSG_MULTI, (2620, 65)),
            msg(TrailingCommaChecker._MSG_MULTI, (2716, 60)),
            msg(TrailingCommaChecker._MSG_MULTI, (2732, 54)),
            msg(TrailingCommaChecker._MSG_MULTI, (2777, 80)),
            msg(TrailingCommaChecker._MSG_MULTI, (2825, 84)),
            msg(TrailingCommaChecker._MSG_MULTI, (2856, 40)),
            msg(TrailingCommaChecker._MSG_MULTI, (2935, 72)),
            msg(TrailingCommaChecker._MSG_MULTI, (2973, 72)),
            msg(TrailingCommaChecker._MSG_MULTI, (3130, 81)),
            msg(TrailingCommaChecker._MSG_MULTI, (3180, 71)),
            msg(TrailingCommaChecker._MSG_MULTI, (3191, 71)),
            msg(TrailingCommaChecker._MSG_MULTI, (3634, 81)),
            msg(TrailingCommaChecker._MSG_MULTI, (3888, 71)),
            msg(TrailingCommaChecker._MSG_MULTI, (3897, 65)),
            msg(TrailingCommaChecker._MSG_MULTI, (4034, 86)),
            msg(TrailingCommaChecker._MSG_MULTI, (4186, 80)),
            msg(TrailingCommaChecker._MSG_MULTI, (4393, 71)),
            msg(TrailingCommaChecker._MSG_MULTI, (4408, 71)),
            msg(UnnecessaryBlankLineAfterColonChecker._MSG, (456, 0)),
        ):
            for checker in self.checkers:
                self.assert_tokens(checker, tokens)
                self.assert_all(checker, node_all)


if __name__ == '__main__':
    main()
