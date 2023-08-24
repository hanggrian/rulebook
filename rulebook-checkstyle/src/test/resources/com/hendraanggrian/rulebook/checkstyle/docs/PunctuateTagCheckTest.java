package com.hendraanggrian.rulebook.checkstyle.docs;

class NoDescription {
    /**
     * @param input
     */
    void add(int input) {}
}

class DescriptionEndsWithPunctuation {
    /**
     * @param input a number.
     * @param input a number!
     * @param input a number?
     * @param input some
     *   long number.
     * @param input some
     *   long number?
     * @param input some
     *   long number!
     */
    void add(int input) {}
}

class TagDescriptionHasNoEndPunctuation {
    /**
     * @param input a number
     * @param input some
     *   long number
     */
    void add(int input) {}
}

class TagDescriptionEndsWithComments {
    /**
     * @param input a number // some comment
     * @param input a. // some comment
     * @param input // some comment
     */
    void add(int input) {}
}
