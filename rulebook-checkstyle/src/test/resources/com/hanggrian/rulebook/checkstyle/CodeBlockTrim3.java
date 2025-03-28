package com.hanggrian.rulebook.checkstyle;

class CodeBlockTrim {
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
