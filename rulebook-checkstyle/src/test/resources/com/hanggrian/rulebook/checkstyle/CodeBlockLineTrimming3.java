package com.hanggrian.rulebook.checkstyle;

public class CodeBlockLineTrimming {
    public class Foo {
        /**
         * Lorem ipsum.
         */
        @Deprecated
        int baz = 0;
    }

    public class Bar {
        /**
         * Lorem ipsum.
         */
        @Deprecated
        public void baz() {}
    }
}
