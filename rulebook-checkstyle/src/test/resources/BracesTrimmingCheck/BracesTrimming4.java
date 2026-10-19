package com.hanggrian.rulebook.checkstyle.checks;

class BracesTrimming {
    class Foo {
        /**
         * Lorem ipsum.
         */
        @Deprecated
        int baz = 0;
    }

    class Bar {
        /**
         * Lorem ipsum.
         */
        @Deprecated
        void baz() {}
    }
}
