### Block tag punctuation

Description of certain block tags, if present, should end with a period,
question mark or exclamation mark.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="3"
    /**
     * @param num
     * @return the new size of the group
     */
    abstract int add(int num);
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    /**
     * @param num
     * @return the new size of the group
     */
    abstract def add(int num)
    ```
=== "Kotlin"

    ```kotlin hl_lines="3"
    /**
     * @param num
     * @return the new size of the group
     */
    abstract fun add(int num): Int
    ```
=== "C/C++"

    ```cpp hl_lines="3"
    /**
     * @param num
     * @return the new size of the group
     */
    virtual int add(int num) = 0;
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="3"
    /**
    * @param num
    * @return the new size of the group.
    */
    abstract int add(int num);
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    /**
    * @param num
    * @return the new size of the group.
    */
    abstract def add(int num)
    ```
=== "Kotlin"

    ```kotlin hl_lines="3"
    /**
    * @param num
    * @return the new size of the group.
    */
    abstract fun add(int num): Int
    ```
=== "C/C++"

    ```cpp hl_lines="3"
    /**
    * @param num
    * @return the new size of the group.
    */
    virtual int add(int num) = 0;
    ```

??? Configuration

    Setting | Default value
    --- | ---
    :material-language-java:{ .lg .middle } `BlockTagPunctuation#tags` | @param, @return
    :simple-apachegroovy:{ .lg .middle } `BlockTagPunctuation#tags` | @param, @return
    :material-language-kotlin:{ .lg .middle } `rulebook_punctuate_block_tags` | @constructor, @receiver, @property, @param, @return
    :material-language-c:{ .lg .middle }:material-language-cpp:{ .lg .middle } `--punctuate-block-tags` | @param, @return

### TODO comment

TODO comment keywords should be uppercase and followed by exactly one space.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    // todo add tests
    //
    // FIXME: memory leak
    ```
=== "Groovy"

    ```groovy
    // todo add tests
    //
    // FIXME: memory leak
    ```
=== "Kotlin"

    ```kotlin
    // todo add tests
    //
    // FIXME: memory leak
    ```
=== "C/C++"

    ```cpp
    // todo add tests
    //
    // FIXME: memory leak
    ```
=== "Python"

    ```python
    # todo add tests
    #
    # FIXME: memory leak
    ```
=== "JavaScript"

    ```js
    // todo add tests
    //
    // FIXME: memory leak
    ```
=== "TypeScript"

    ```ts
    // todo add tests
    //
    // FIXME: memory leak
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    // TODO add tests
    //
    // FIXME memory leak
    ```
=== "Groovy"

    ```groovy
    // TODO add tests
    //
    // FIXME memory leak
    ```
=== "Kotlin"

    ```kotlin
    // TODO add tests
    //
    // FIXME memory leak
    ```
=== "C/C++"

    ```cpp
    // TODO add tests
    //
    // FIXME memory leak
    ```
=== "Python"

    ```python
    # TODO add tests
    #
    # FIXME memory leak
    ```
=== "JavaScript"

    ```js
    // TODO add tests
    //
    // FIXME memory leak
    ```
=== "TypeScript"

    ```ts
    // TODO add tests
    //
    // FIXME memory leak
    ```

### Trailing comma in call

Put a trailing comma in a multiline call site, omit when it is a single line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"

    ```groovy hl_lines="4 7"
    var items =
        [
            'milks',
            'eggs'
        ] as Set

    println(items,)
    ```
=== "Kotlin"

    ```kotlin hl_lines="4 7"
    val items =
        setOf(
            "milks",
            "eggs"
        )

    println(items,)
    ```
=== "Python"

    ```python hl_lines="4 7"
    items = \
        set(
            'milks',
            'eggs'
        )

    print(items,)
    ```
=== "JavaScript"

    ```js hl_lines="4 7"
    const items =
        new Set([
            'milks',
            'eggs'
        ]);

    console.log(items,);
    ```
=== "TypeScript"

    ```ts hl_lines="4 7"
    const items: Set<string> =
        new Set([
            'milks',
            'eggs'
        ]);

    console.log(items,);
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"

    ```groovy hl_lines="4 7"
    var items =
        [
            'milks',
            'eggs',
        ] as Set

    println(items)
    ```
=== "Kotlin"

    ```kotlin hl_lines="4 7"
    val items =
        setOf(
            "milks",
            "eggs",
        )

    println(items)
    ```
=== "Python"

    ```python hl_lines="4 7"
    items = \
        set(
            'milks',
            'eggs',
        )

    print(items)
    ```
=== "JavaScript"

    ```js hl_lines="4 7"
    const items =
        new Set([
            'milks',
            'eggs',
        ]);

    console.log(items);
    ```
=== "TypeScript"

    ```ts hl_lines="4 7"
    const items: Set<string> =
        new Set([
            'milks',
            'eggs',
        ]);

    console.log(items);
    ```

### Trailing comma in collection

Put a trailing comma in a multiline collection site, omit when it is a single
line. In Java and Groovy, this rule applies to array initializers. In Python,
this rule applies to tuples.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2-4"
    int[][] ticTacToe = {
        {0, 0, 0,},
        {0, 0, 0,},
        {0, 0, 0,}
    };
    ```
=== "Groovy"

    ```groovy hl_lines="2-4"
    var ticTacToe = [
        [0, 0, 0,],
        [0, 0, 0,],
        [0, 0, 0,]
    ]
    ```
=== "Python"

    ```python hl_lines="2-4"
    tic_tac_toe = (
        (0, 0, 0,),
        (0, 0, 0,),
        (0, 0, 0,)
    )
    ```
=== "JavaScript"

    ```js hl_lines="2-4"
    const ticTacToe = [
        [0, 0, 0,],
        [0, 0, 0,],
        [0, 0, 0,]
    ];
    ```
=== "TypeScript"

    ```ts hl_lines="2-4"
    const ticTacToe: number[][] = [
        [0, 0, 0,],
        [0, 0, 0,],
        [0, 0, 0,]
    ];
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="2-4"
    int[][] ticTacToe = {
        {0, 0, 0},
        {0, 0, 0},
        {0, 0, 0},
    };
    ```
=== "Groovy"

    ```groovy hl_lines="2-4"
    var ticTacToe = [
        [0, 0, 0],
        [0, 0, 0],
        [0, 0, 0],
    ]
    ```
=== "Python"

    ```python hl_lines="2-4"
    tic_tac_toe = (
        (0, 0, 0),
        (0, 0, 0),
        (0, 0, 0),
    )
    ```
=== "JavaScript"

    ```js hl_lines="2-4"
    const ticTacToe = [
        [0, 0, 0],
        [0, 0, 0],
        [0, 0, 0],
    ];
    ```
=== "TypeScript"

    ```ts hl_lines="2-4"
    const ticTacToe: number[][] = [
        [0, 0, 0],
        [0, 0, 0],
        [0, 0, 0],
    ];
    ```

### Trailing comma in declaration

Put a trailing comma in a multiline declaration site, omit when it is a single
line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"

    ```kotlin hl_lines="1 5"
    fun updateInventory(item: String,) {}

    fun createInventory(
        item: String,
        quantity: Int
    ) {}
    ```
=== "Python"

    ```python hl_lines="1 7"
    def update_inventory(item,):
        pass


    def create_inventory(
        item,
        quantity
    ):
        pass
    ```
=== "JavaScript"

    ```js hl_lines="1 5"
    function updateInventory(item,) {}

    function createInventory(
        item,
        quantity
    ) {}
    ```
=== "TypeScript"

    ```ts hl_lines="1 5"
    function updateInventory(item: string,): void {}

    function createInventory(
        item: string,
        quantity: number
    ): void {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"

    ```kotlin hl_lines="1 5"
    fun updateInventory(item: String) {}

    fun createInventory(
        item: String,
        quantity: Int,
    ) {}
    ```
=== "Python"

    ```python hl_lines="1 7"
    def update_inventory(item):
        pass


    def create_inventory(
        item,
        quantity,
    ):
        pass
    ```
=== "JavaScript"

    ```js hl_lines="1 5"
    function updateInventory(item) {}

    function createInventory(
        item,
        quantity,
    ) {}
    ```
=== "TypeScript"

    ```ts hl_lines="1 5"
    function updateInventory(item: string): void {}

    function createInventory(
        item: string,
        quantity: number,
    ): void {}
    ```

### Unused import

Remove unused import statements.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2"
    import com.example.fruit.Apple;
    import com.example.fruit.Banana;

    Apple apple = new Apple();
    ```
=== "Groovy"

    ```groovy hl_lines="2"
    import com.example.fruit.Apple
    import com.example.fruit.Banana

    var apple = new Apple()
    ```
=== "Kotlin"

    ```kotlin hl_lines="2"
    import com.example.fruit.Apple
    import com.example.fruit.Banana

    val apple = Apple()
    ```
=== "Python"

    ```python hl_lines="1"
    from fruit import Apple, Banana

    apple = Apple()
    ```
=== "JavaScript"

    ```js hl_lines="1"
    import { Apple, Banana } from 'fruit';

    const apple = new Apple();
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    import { Apple, Banana } from 'fruit';

    const apple: Apple = new Apple();
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    import com.example.fruit.Apple;

    Apple apple = new Apple();
    ```
=== "Groovy"

    ```groovy
    import com.example.fruit.Apple

    var apple = new Apple()
    ```
=== "Kotlin"

    ```kotlin
    import com.example.fruit.Apple

    val apple = Apple()
    ```
=== "Python"

    ```python hl_lines="1"
    from fruit import Apple

    apple = Apple()
    ```
=== "JavaScript"

    ```js hl_lines="1"
    import { Apple } from 'fruit';

    const apple = new Apple();
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    import { Apple } from 'fruit';

    const apple: Apple = new Apple();
    ```

### Wildcard import

Import directives must be single-type instead of wildcard imports.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="1"
    import com.example.fruit.*;

    List<Fruit> fruits = Arrays.asList(new Apple(), new Banana());
    ```
=== "Groovy"

    ```groovy hl_lines="1"
    import com.example.fruit.*

    var fruits = [new Apple(), new Banana()]
    ```
=== "Kotlin"

    ```kotlin hl_lines="1"
    import com.example.fruit.*

    val fruits = listOf(Apple(), Banana())
    ```
=== "Python"

    ```python hl_lines="1"
    from fruit import *

    fruits = [Apple(), Banana()]
    ```
=== "JavaScript"

    ```js hl_lines="1"
    import * as fruit from 'fruit';

    const fruits = [new fruit.Apple(), new fruit.Banana()];
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    import * as fruit from 'fruit';

    const fruits: Fruit[] = [new fruit.Apple(), new fruit.Banana()];
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="1-2"
    import com.example.fruit.Apple;
    import com.example.fruit.Banana;

    List<Fruit> fruits = Arrays.asList(new Apple(), new Banana());
    ```
=== "Groovy"

    ```groovy hl_lines="1-2"
    import com.example.fruit.Apple
    import com.example.fruit.Banana

    var fruits = [new Apple(), new Banana()]
    ```
=== "Kotlin"

    ```kotlin hl_lines="1-2"
    import com.example.fruit.Apple
    import com.example.fruit.Banana

    val fruits = listOf(Apple(), Banana())
    ```
=== "Python"

    ```python hl_lines="1"
    from fruit import Apple, Banana

    fruits = [Apple(), Banana()]
    ```
=== "JavaScript"

    ```js hl_lines="1"
    import { Apple, Banana } from 'fruit';

    const fruits = [new Apple(), new Banana()];
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    import { Apple, Banana, Fruit } from 'fruit';

    const fruits: Fruit[] = [new Apple(), new Banana()];
    ```

## Clipping

### Block comment clip

Short block comments should be written in a single line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    /**
     * The quick brown fox jumps over the lazy dog.
     */
    ```
=== "Groovy"

    ```groovy
    /**
     * The quick brown fox jumps over the lazy dog.
     */
    ```
=== "Kotlin"

    ```kotlin
    /**
     * The quick brown fox jumps over the lazy dog.
     */
    ```
=== "Python"

    ```python
    """
    The quick brown fox jumps over the lazy dog.
    """
    ```
=== "JavaScript"

    ```js
    /**
     * The quick brown fox jumps over the lazy dog.
     */
    ```
=== "TypeScript"

    ```ts
    /**
     * The quick brown fox jumps over the lazy dog.
     */
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    /** The quick brown fox jumps over the lazy dog. */
    ```
=== "Groovy"

    ```groovy
    /** The quick brown fox jumps over the lazy dog. */
    ```
=== "Kotlin"

    ```kotlin
    /** The quick brown fox jumps over the lazy dog. */
    ```
=== "Python"

    ```python
    """The quick brown fox jumps over the lazy dog."""
    ```
=== "JavaScript"

    ```js
    /** The quick brown fox jumps over the lazy dog. */
    ```
=== "TypeScript"

    ```ts
    /** The quick brown fox jumps over the lazy dog. */
    ```

### Braces clip

Empty code blocks should be joined with the preceding code.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2"
    void main() {
    }
    ```
=== "Groovy"

    ```groovy hl_lines="2"
    def main() {
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="2"
    fun main() {
    }
    ```
=== "C/C++"

    ```cpp hl_lines="2"
    void main() {
    }
    ```
=== "Python"

    ```python hl_lines="2"
    foo = {
    }
    ```
=== "JavaScript"

    ```js hl_lines="2"
    function main() {
    }
    ```
=== "TypeScript"

    ```ts hl_lines="2"
    function main(): void {
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="1"
    void main() {}
    ```
=== "Groovy"

    ```groovy hl_lines="1"
    def main() {}
    ```
=== "Kotlin"

    ```kotlin hl_lines="1"
    fun main() {}
    ```
=== "C/C++"

    ```cpp hl_lines="1"
    void main() {}
    ```
=== "Python"

    ```python hl_lines="1"
    foo = {}
    ```
=== "JavaScript"

    ```js hl_lines="1"
    function main() {}
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    function main(): void {}
    ```

### Brackets clip

Empty collection initializers should be joined with the preceding code.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"

    ```groovy hl_lines="2"
    var numbers = [
    ]
    ```
=== "C/C++"

    ```cpp hl_lines="2"
    std::vector<int> numbers = {
    };
    ```
=== "Python"

    ```python hl_lines="2"
    numbers = [
    ]
    ```
=== "JavaScript"

    ```js hl_lines="2"
    const numbers = [
    ];
    ```
=== "TypeScript"

    ```ts hl_lines="2"
    const numbers: number[] = [
    ];
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"

    ```groovy hl_lines="1"
    var numbers = []
    ```
=== "C/C++"

    ```cpp hl_lines="1"
    std::vector<int> numbers = {};
    ```
=== "Python"

    ```python hl_lines="1"
    numbers = []
    ```
=== "JavaScript"

    ```js hl_lines="1"
    const numbers = [];
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    const numbers: number[] = [];
    ```

### Parentheses clip

Empty method declarations and calls should be joined with the preceding code.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2 4"
    void recurse(
    ) {
        recurse(
        );
    }
    ```
=== "Groovy"

    ```groovy hl_lines="2 4"
    def recurse(
    ) {
        recurse(
        )
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="2 4"
    fun recurse(
    ) {
        recurse(
        )
    }
    ```
=== "C/C++"

    ```cpp hl_lines="2 4"
    void recurse(
    ) {
        recurse(
        );
    }
    ```
=== "Python"

    ```python hl_lines="2 4"
    def recurse(
    ):
        recurse(
        )
    ```
=== "JavaScript"

    ```js hl_lines="2 4"
    function recurse(
    ) {
        recurse(
        );
    }
    ```
=== "TypeScript"

    ```ts hl_lines="2 4"
    function recurse(
    ): void {
        recurse(
        );
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="1-2"
    void recurse() {
        recurse();
    }
    ```
=== "Groovy"

    ```groovy hl_lines="1-2"
    def recurse() {
        recurse()
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="1-2"
    fun recurse() {
        recurse()
    }
    ```
=== "C/C++"

    ```cpp hl_lines="1-2"
    void recurse() {
        recurse();
    }
    ```
=== "Python"

    ```python hl_lines="1-2"
    def recurse():
        recurse()
    ```
=== "JavaScript"

    ```js hl_lines="1-2"
    function recurse() {
        recurse();
    }
    ```
=== "TypeScript"

    ```ts hl_lines="1-2"
    function recurse(): void {
        recurse();
    }
    ```

### Tags clip

Empty generic types should be joined with the preceding code.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2-3"
    List<Float> points =
        new ArrayList<
            >();
    ```
=== "Groovy"

    ```groovy hl_lines="2-3"
    var points =
        new ArrayList<
            >()
    ```
=== "C/C++"

    ```cpp hl_lines="1-2"
    template<
    > int points() {
        return 0;
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="2"
    List<Float> points =
        new ArrayList<>();
    ```
=== "Groovy"

    ```groovy hl_lines="2"
    var points =
        new ArrayList<>()
    ```
=== "C/C++"

    ```cpp hl_lines="1"
    template<> int points() {
        return 0;
    }
    ```

## Declaring

### Deprecated type

Prefer to use built-in types provided by the language.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"

    ```kotlin hl_lines="1 4"
    import java.util.ArrayList

    val names = arrayListOf<String>()
    val people = java.util.ArrayList<Person>()
    ```
=== "Python"

    ```python hl_lines="1 3"
    from typing import Optional

    def get_name(person) -> Optional[str]:
        return person['name']
    ```
=== "TypeScript"

    ```ts hl_lines="1 3"
    type Nullable<T> = T | null;

    function getName(person: any): Nullable<string> {
        return person.name;
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"

    ```kotlin hl_lines="2"
    val names = arrayListOf<String>()
    val people = ArrayList<Person>()
    ```
=== "Python"

    ```python hl_lines="1"
    def get_name(person) -> str | None:
        return person['name']
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    function getName(person: any): string | null {
        return person.name;
    }
    ```

### Double quotes in block comment

Use double quotes in Python docstrings.

**:material-star-four-points-outline:{ #accent } Before**

=== "Python"

    ```python hl_lines="1"
    '''
    The quick brown fox jumps over the lazy dog.
    '''
    ```

**:material-star-four-points:{ #accent } After**

=== "Python"

    ```python hl_lines="1"
    """
    The quick brown fox jumps over the lazy dog.
    """
    ```

### Internal error

Use `Exception` as superclass of custom exceptions. Most applications should not
extend `Error` or `Throwable`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    class PurchaseException extends Error {}
    ```
=== "Groovy"

    ```groovy
    class PurchaseException extends Error {}
    ```
=== "Kotlin"

    ```kotlin
    class PurchaseException : Error()
    ```
=== "Python"

    ```python
    class PurchaseException(BaseException):
        pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    class PurchaseException extends Exception {}
    ```
=== "Groovy"

    ```groovy
    class PurchaseException extends Exception {}
    ```
=== "Kotlin"

    ```kotlin
    class PurchaseException : Exception()
    ```
=== "Python"

    ```python
    class PurchaseException(Exception):
        pass
    ```

### Lowercase d

Double floating point literals should be suffixed with lowercase `d`, which is
more readable than `D`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    double quarter = 0.25D;
    ```
=== "Groovy"

    ```groovy
    var quarter = 0.25D
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    double quarter = 0.25d;
    ```
=== "Groovy"

    ```groovy
    var quarter = 0.25d
    ```

### Lowercase f

Floating point literals should be suffixed with lowercase `f`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    float half = 0.5F;
    ```
=== "Groovy"

    ```groovy
    var quarter = 0.25F
    ```
=== "Kotlin"

    ```kotlin
    val half = 0.5F
    ```
=== "C/C++"

    ```cpp
    float half = 0.5F;
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    float half = 0.5f;
    ```
=== "Groovy"

    ```groovy
    var quarter = 0.25f
    ```
=== "Kotlin"

    ```kotlin
    val half = 0.5f
    ```
=== "C/C++"

    ```cpp
    float half = 0.5f;
    ```

### Lowercase Hexadecimal

All letters in hexadecimal literals should be lowercase.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    int color = 0xFF00FF;
    ```
=== "Groovy"

    ```groovy
    var color = 0xFF00FF
    ```
=== "Kotlin"

    ```kotlin
    val color = 0xFF00FF
    ```
=== "C/C++"

    ```cpp
    int color = 0xFF00FF;
    ```
=== "Python"

    ```python
    color = 0xFF00FF
    ```
=== "JavaScript"

    ```js
    const color = 0xFF00FF;
    ```
=== "TypeScript"

    ```ts
    const color: number = 0xFF00FF;
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    int color = 0xff00ff;
    ```
=== "Groovy"

    ```groovy
    var color = 0xff00ff
    ```
=== "Kotlin"

    ```kotlin
    val color = 0xff00ff
    ```
=== "C/C++"

    ```cpp
    int color = 0xff00ff;
    ```
=== "Python"

    ```python
    color = 0xff00ff
    ```
=== "JavaScript"

    ```js
    const color = 0xff00ff;
    ```
=== "TypeScript"

    ```ts
    const color: number = 0xff00ff;
    ```

### Lowercase i

Integer literals should be suffixed with lowercase `i`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"

    ```groovy
    var ten = 10I
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"

    ```groovy
    var ten = 10i
    ```

### Missing inline in contract

Kotlin contract functions that carry a runnable parameter should have `inline`
modifier. Without the modifier, user cannot assign a global variable within the
code block.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"

    ```kotlin hl_lines="1"
    fun action(block: () -> Unit) {
        contract { callsInPlace(block, EXACTLY_ONCE) }
        block()
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"

    ```kotlin hl_lines="1"
    inline fun action(block: () -> Unit) {
        contract { callsInPlace(block, EXACTLY_ONCE) }
        block()
    }
    ```

### Missing private constructor

Utility classes should have a final modifier and a private constructor to
prevent instantiation.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="1"
    class Lists {
        static List<String> of(String... elements) {
            return Arrays.asList(elements);
        }
    }
    ```
=== "Groovy"

    ```groovy hl_lines="1"
    class Lists {
        static List<String> of(String... elements) {
            return Arrays.asList(elements)
        }
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="1-2"
    final class Lists {
        private Lists() {}

        static List<String> of(String... elements) {
            return Arrays.asList(elements);
        }
    }
    ```
=== "Groovy"

    ```groovy hl_lines="1-2"
    final class Lists {
        private Lists() {}

        static List<String> of(String... elements) {
            return Arrays.asList(elements)
        }
    }
    ```

### Redundant qualifier

Strip fully qualified names when they are already imported.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="3"
    import java.io.FileInputStream;

    void read(java.io.FileInputStream stream) {}
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    import java.io.FileInputStream

    def read(java.io.FileInputStream stream) {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="3"
    import java.io.FileInputStream;

    void read(FileInputStream stream) {}
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    import java.io.FileInputStream

    def read(FileInputStream stream) {}
    ```

### Single quotes in literal

Wrap string in single quotes, unless there is a template or a single quote.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"

    ```groovy
    var name = "John Doe"

    println('G\'day, ' + name)
    ```
=== "Python"

    ```python
    name = "John Doe"

    print('G\'day, ' + name)
    ```
=== "JavaScript"

    ```js
    const name = "John Doe";

    console.log('G\'day, ' + name);
    ```
=== "TypeScript"

    ```ts
    const name: string = "John Doe";

    console.log('G\'day, ' + name);
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"

    ```groovy
    var name = 'John Doe'

    println("G'day, " + name)
    ```
=== "Python"

    ```python
    name = 'John Doe'

    print("G'day, " + name)
    ```
=== "JavaScript"

    ```js
    const name = 'John Doe';

    console.log(`G'day, ${name}`);
    ```
=== "TypeScript"

    ```ts
    const name: string = 'John Doe';

    console.log(`G'day, ${name}`);
    ```

### Unnecessary abstract

Abstract classes need at least one abstract function.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="1"
    abstract class Vehicle {
        void start() {}
    }
    ```
=== "Groovy"

    ```groovy hl_lines="1"
    abstract class Vehicle {
        def start() {}
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="1"
    abstract class Vehicle {
        fun start() {}
    }
    ```
=== "Python"

    ```python hl_lines="3"
    from abc import ABC

    class Vehicle(ABC):
        def start(self):
            pass
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    abstract class Vehicle {
        start() {}
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="1"
    class Vehicle {
        void start() {}
    }
    ```
=== "Groovy"

    ```groovy hl_lines="1"
    class Vehicle {
        def start() {}
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="1"
    class Vehicle {
        fun start() {}
    }
    ```
=== "Python"

    ```python hl_lines="1"
    class Vehicle:
        def start(self):
            pass
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    class Vehicle {
        start() {}
    }
    ```

### Unnecessary parentheses in lambda

Single parameter lambdas should not have parentheses.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    files.forEach((file) -> System.out.println(file));
    ```
=== "Groovy"

    ```groovy
    files.forEach((file) -> System.out.println(file))
    ```
=== "JavaScript"

    ```js
    files.forEach((file) => console.log(file));
    ```
=== "TypeScript"

    ```ts
    files.forEach((file: string) => console.log(file));
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    files.forEach(file -> System.out.println(file));
    ```
=== "Groovy"

    ```groovy
    files.forEach(file -> System.out.println(file))
    ```
=== "JavaScript"

    ```js
    files.forEach(file => console.log(file));
    ```
=== "TypeScript"

    ```ts
    files.forEach((file: string) => console.log(file));
    ```

!!! tip

    Parentheses on lambda parameters is a syntax error in:

    - Kotlin if parameter is single
    - Groovy closures, not lambdas

Use single quotes for string literals, unless the string contains single quotes.

### Uppercase L

Long integer literals should be suffixed with uppercase `L`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    long tenMillion = 10_000_000l;
    ```
=== "Groovy"

    ```groovy
    var tenMillion = 10_000_000l
    ```
=== "C/C++"

    ```cpp
    long ten_million = 10'000'000l;
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    long tenMillion = 10_000_000L;
    ```
=== "Groovy"

    ```groovy
    var tenMillion = 10_000_000L
    ```
=== "C/C++"

    ```cpp
    long ten_million = 10'000'000L;
    ```

## Expressing

### Complicated boolean equality

Simplify boolean expressions when applicable.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    boolean valid = isReady() == true && isRunning() == false;
    ```
=== "Groovy"

    ```groovy
    var valid = isReady() == true && isRunning() == false
    ```
=== "Kotlin"

    ```kotlin
    val valid = isReady() == true && isRunning() == false
    ```
=== "Python"

    ```python
    valid = is_ready() == True and is_running() == False
    ```
=== "TypeScript"

    ```ts
    const valid: boolean = isReady() === true && isRunning() === false;
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    boolean valid = isReady() && !isRunning();
    ```
=== "Groovy"

    ```groovy
    var valid = isReady() && !isRunning()
    ```
=== "Kotlin"

    ```kotlin
    val valid = isReady() && !isRunning()
    ```
=== "Python"

    ```python
    valid = is_ready() and not is_running()
    ```
=== "TypeScript"

    ```ts
    const valid: boolean = isReady() && !isRunning();
    ```

### Complicated size equality

Use `isEmpty()` or `isNotEmpty()` instead of comparing collection size with
zero. In languages with truthy values, use the collection itself in a boolean
context.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="1"
    if (files.size() > 0) {
        collect(files);
    }
    ```
=== "Groovy"

    ```groovy hl_lines="1"
    if (files.size() > 0) {
        collect(files)
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="1"
    if (files.size > 0) {
        collect(files)
    }
    ```
=== "Python"

    ```python hl_lines="1"
    if len(files) > 0:
        collect(files)
    ```
=== "JavaScript"

    ```js hl_lines="1"
    if (files.length > 0) {
        collect(files);
    }
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    if (files.length > 0) {
        collect(files);
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="1"
    if (!files.isEmpty()) {
        collect(files);
    }
    ```
=== "Groovy"

    ```groovy hl_lines="1"
    if (!files.isEmpty()) {
        collect(files)
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="1"
    if (files.isNotEmpty()) {
        collect(files)
    }
    ```
=== "Python"

    ```python hl_lines="1"
    if files:
        collect(files)
    ```
=== "JavaScript"

    ```js hl_lines="1"
    if (files.length) {
        collect(files);
    }
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    if (files.length) {
        collect(files);
    }
    ```

### Confusing predicate

Use the positive form in a predicate call when it is a single expression and the
calling function can be inverted.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"

    ```kotlin
    person.takeUnless { it.name != null }
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"

    ```kotlin
    person.takeIf { it.name == null }
    ```

### Deprecated identity

Use structural equality instead of referential equality when comparing with
primitive values. In Kotlin, `null` is included in the set of primitive values.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"

    ```kotlin
    user.takeUnless { it.name === null }
    ```
=== "Python"

    ```python
    if len(user.addresses) is 1:
        continue
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"

    ```kotlin
    user.takeUnless { it.name == null }
    ```
=== "Python"

    ```python
    if len(user.addresses) == 1:
        continue
    ```

### Redundant equality

Compare primitives with identity operator `===`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"

    ```groovy hl_lines="1"
    if (total == 0) {
        return
    }
    ```
=== "JavaScript"

    ```js hl_lines="1"
    if (total == 0) {
        return;
    }
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    if (total == 0) {
        return;
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"

    ```groovy hl_lines="1"
    if (total === 0) {
        return
    }
    ```
=== "JavaScript"

    ```js hl_lines="1"
    if (total === 0) {
        return;
    }
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    if (total === 0) {
        return;
    }
    ```

### Redundant equals

Use explicit operator instead of named function in Groovy.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"

    ```groovy hl_lines="1"
    if (total.equals(0)) {
        return
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"

    ```groovy hl_lines="1"
    if (total == 0) {
        return
    }
    ```

## Formatting

### Empty file

Empty files should be removed. JVM source files with only package declaration
is considered empty.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    package awesome.company;
    ```
=== "Groovy"

    ```groovy
    package awesome.company
    ```
=== "Kotlin"

    ```kotlin
    package awesome.company
    ```
=== "Python"

    ```python

    ```
=== "JavaScript"

    ```js

    ```
=== "TypeScript"

    ```ts

    ```

### File size

File length should not be longer than 1.000 lines of code. If a file exceeds the
limit, it should be split into multiple files.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    final class Articles {
        static int create(Article article) { /*...*/ }

        static Article read(int articleId) { /*...*/ }

        static void update(int articleId, Article article) { /*...*/ }

        static void delete(int articleId) { /*...*/ }
    }
    ```
=== "Groovy"

    ```groovy
    final class Articles {
        static def create(Article article) { /*...*/ }

        static def read(int articleId) { /*...*/ }

        static def update(int articleId, Article article) { /*...*/ }

        static def delete(int articleId) { /*...*/ }
    }
    ```
=== "Kotlin"

    ```kotlin
    object Articles {
        fun create(article: Article): Int { /*...*/ }

        fun read(articleId: Int): Article { /*...*/ }

        fun update(articleId: Int, article: Article) { /*...*/ }

        fun delete(articleId: Int) { /*...*/ }
    }
    ```
=== "C/C++"

    ```cpp
    int create_article(Article article) {
        // ...
    }

    Article read_article(int article_id) {
        // ...
    }

    void update_article(int article_id, Article article) {
        // ...
    }

    void delete_article(int article_id) {
        // ...
    }
    ```
=== "Python"

    ```python
    def create_article(article):
        // ...

    def read_article(article_id):
        // ...

    def update_article(article_id, article):
        // ...

    def delete_article(article_id):
        // ...
    ```
=== "JavaScript"

    ```js
    function createArticle(article) {
        // ...
    }

    function readArticle(articleId) {
        // ...
    }

    function updateArticle(articleId, article) {
        // ...
    }

    function deleteArticle(articleId) {
        // ...
    }
    ```
=== "TypeScript"

    ```ts
    function createArticle(article: Article): number {
        // ...
    }

    function readArticle(articleId: number): Article {
        // ...
    }

    function updateArticle(articleId: number, article: Article): void {
        // ...
    }

    function deleteArticle(articleId: number): void {
        // ...
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    final class ArticleCreator {
        static int create(Article article) { /*...*/ }
    }
    ```
    ```java
    final class ArticleReader {
        static Article read(int articleId) { /*...*/ }
    }
    ```
    ```java
    final class ArticleUpdater {
        static void update(int articleId, Article article) { /*...*/ }
    }
    ```
    ```java
    final class ArticleDeleter {
        static void delete(int articleId) { /*...*/ }
    }
    ```
=== "Groovy"

    ```groovy
    final class ArticleCreator {
        static def create(Article article) { /*...*/ }
    }
    ```
    ```groovy
    final class ArticleReader {
        static def read(int articleId) { /*...*/ }
    }
    ```
    ```groovy
    final class ArticleUpdater {
        static def update(int articleId, Article article) { /*...*/ }
    }
    ```
    ```groovy
    final class ArticleDeleter {
        static def delete(int articleId) { /*...*/ }
    }
    ```
=== "Kotlin"

    ```kotlin
    fun Articles.create(article: Article): Int { /*...*/ }
    ```
    ```kotlin
    fun Articles.read(articleId: Int): Article { /*...*/ }
    ```
    ```kotlin
    fun Articles.update(articleId: Int, article: Article) { /*...*/ }
    ```
    ```kotlin
    fun Articles.delete(articleId: Int) { /*...*/ }
    ```
=== "C/C++"

    ```cpp
    int create_article(Article article) {
        // ...
    }
    ```
    ```cpp
    Article read_article(int article_id) {
        // ...
    }
    ```
    ```cpp
    void update_article(int article_id, Article article) {
        // ...
    }
    ```
    ```cpp
    void delete_article(int article_id) {
        // ...
    }
    ```
=== "Python"

    ```python
    def create_article(article):
        // ...
    ```
    ```python
    def read_article(article_id):
        // ...
    ```
    ```python
    def update_article(article_id, article):
        // ...
    ```
    ```python
    def delete_article(article_id):
        // ...
    ```
=== "JavaScript"

    ```js
    function createArticle(article) {
        // ...
    }
    ```
    ```js
    function readArticle(articleId) {
        // ...
    }
    ```
    ```js
    function updateArticle(articleId, article) {
        // ...
    }
    ```
    ```js
    function deleteArticle(articleId) {
        // ...
    }
    ```
=== "TypeScript"

    ```ts
    function createArticle(article: Article): number {
        // ...
    }
    ```
    ```ts
    function readArticle(articleId: number): Article {
        // ...
    }
    ```
    ```ts
    function updateArticle(articleId: number, article: Article): void {
        // ...
    }
    ```
    ```ts
    function deleteArticle(articleId: number): void {
        // ...
    }
    ```

??? Configuration

    Setting | Default value
    --- | ---
    :material-language-java:{ .lg .middle } `FileLength#max` | 1.000
    :simple-apachegroovy:{ .lg .middle } `ClassSize#maxLines` | 1.000
    :material-language-kotlin:{ .lg .middle } `rulebook_max_file_size` | 1.000
    :material-language-c:{ .lg .middle }:material-language-cpp:{ .lg .middle } `--max-file-size` | 1.000
    :material-language-python:{ .lg .middle } `rulebook-max-file-size` | 1.000
    :material-language-javascript:{ .lg .middle } `max-lines` | 1.000
    :material-language-typescript:{ .lg .middle } `max-lines` | 1.000

### Final newline

End files with a newline character.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    class AwesomeImplementation { /*...*/ }
    ```
=== "Groovy"

    ```groovy
    class AwesomeImplementation { /*...*/ }
    ```
=== "Kotlin"

    ```kotlin
    class AwesomeImplementation { /*...*/ }
    ```
=== "C/C++"

    ```cpp
    class AwesomeImplementation { /*...*/ }
    ```
=== "Python"

    ```python
    class AwesomeImplementation:
        pass
    ```
=== "JavaScript"

    ```js
    class AwesomeImplementation { /*...*/ }
    ```
=== "TypeScript"

    ```ts
    class AwesomeImplementation { /*...*/ }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="2"
    class AwesomeImplementation { /*...*/ }
    \n
    ```
=== "Groovy"

    ```groovy hl_lines="2"
    class AwesomeImplementation { /*...*/ }
    \n
    ```
=== "Kotlin"

    ```kotlin hl_lines="2"
    class AwesomeImplementation { /*...*/ }
    \n
    ```
=== "C/C++"

    ```cpp hl_lines="2"
    class AwesomeImplementation { /*...*/ }
    \n
    ```
=== "Python"

    ```python hl_lines="3"
    class AwesomeImplementation:
        pass
    \n
    ```
=== "JavaScript"

    ```js hl_lines="2"
    class AwesomeImplementation { /*...*/ }
    \n
    ```
=== "TypeScript"

    ```ts hl_lines="2"
    class AwesomeImplementation { /*...*/ }
    \n
    ```

### Indent style

Use spaces instead of tabs for indentation.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2-4"
    class Subscriber {
    \t void subscribe() {
    \t\t publisher.subscribe(this);
    \t }
    }
    ```
=== "Groovy"

    ```groovy hl_lines="2-4"
    class Subscriber {
    \t def subscribe() {
    \t\t publisher.subscribe(this)
    \t }
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="2-4"
    class Subscriber {
    \t fun subscribe() {
    \t\t publisher.subscribe(this)
    \t }
    }
    ```
=== "C/C++"

    ```cpp hl_lines="2-4"
    class Subscriber {
    \t void subscribe() {
    \t\t publisher.subscribe(this);
    \t }
    }
    ```
=== "Python"

    ```python hl_lines="2-3"
    class Subscriber:
    \t def subscribe(self):
    \t\t publisher.subscribe(self)
    ```
=== "JavaScript"

    ```js hl_lines="2-4"
    class Subscriber {
    \t subscribe() {
    \t\t publisher.subscribe(this);
    \t }
    }
    ```
=== "TypeScript"

    ```ts hl_lines="2-4"
    class Subscriber {
    \t subscribe(): void {
    \t\t publisher.subscribe(this);
    \t }
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="2-4"
    class Subscriber {
        void subscribe() {
            publisher.subscribe(this);
        }
    }
    ```
=== "Groovy"

    ```groovy hl_lines="2-4"
    class Subscriber {
        def subscribe() {
            publisher.subscribe(this)
        }
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="2-4"
    class Subscriber {
        fun subscribe() {
            publisher.subscribe(this)
        }
    }
    ```
=== "C/C++"

    ```cpp hl_lines="2-4"
    class Subscriber {
        void subscribe() {
            publisher.subscribe(this);
        }
    }
    ```
=== "Python"

    ```python hl_lines="2-3"
    class Subscriber:
        def subscribe(self):
            publisher.subscribe(self)
    ```
=== "JavaScript"

    ```js hl_lines="2-4"
    class Subscriber {
        subscribe() {
            publisher.subscribe(this);
        }
    }
    ```
=== "TypeScript"

    ```ts hl_lines="2-4"
    class Subscriber {
        subscribe(): void {
            publisher.subscribe(this);
        }
    }
    ```

### Line feed

Apply Unix-style line feed (LF) as a line separator.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    class AwesomeImplementation { /*...*/ }\r\n
    ```
=== "Groovy"

    ```groovy
    class AwesomeImplementation { /*...*/ }\r\n
    ```
=== "Python"

    ```python hl_lines="2"
    class AwesomeImplementation:
        pass\r\n
    ```
=== "JavaScript"

    ```js
    class AwesomeImplementation { /*...*/ }\r\n
    ```
=== "TypeScript"

    ```ts
    class AwesomeImplementation { /*...*/ }\r\n
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    class AwesomeImplementation { /*...*/ }\n
    ```
=== "Groovy"

    ```groovy
    class AwesomeImplementation { /*...*/ }\n
    ```
=== "Python"

    ```python hl_lines="2"
    class AwesomeImplementation:
        pass\n
    ```
=== "JavaScript"

    ```js
    class AwesomeImplementation { /*...*/ }\n
    ```

=== "TypeScript"

    ```ts
    class AwesomeImplementation { /*...*/ }\n
    ```

### Line length

Length of a line should not exceed certain number of characters.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    StringBuilder builder = new StringBuilder("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    ```
=== "Groovy"

    ```groovy
    var builder = new StringBuilder('Lorem ipsum dolor sit amet, consectetur adipiscing elit.')
    ```
=== "Kotlin"

    ```kotlin
    val builder = StringBuilder("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
    ```
=== "C/C++"

    ```cpp
    std::string builder = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
    ```
=== "Python"

    ```python
    builder = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
    ```
=== "JavaScript"

    ```js
    const builder = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.';
    ```
=== "TypeScript"

    ```ts
    const builder: string = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.';
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    StringBuilder builder =
        new StringBuilder(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
        );
    ```
=== "Groovy"

    ```groovy
    var builder =
        new StringBuilder(
            'Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
        )
    ```
=== "Kotlin"

    ```kotlin
    val builder =
        StringBuilder(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
        )
    ```
=== "C/C++"

    ```cpp
    std::string builder =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
    ```
=== "Python"

    ```python
    builder = \
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
    ```
=== "JavaScript"

    ```js
    const builder =
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit.';
    ```
=== "TypeScript"

    ```ts
    const builder: string =
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit.';
    ```

??? Configuration

    Setting | Default value
    --- | ---
    :material-language-java:{ .lg .middle } `LineLength#max` | 100
    :simple-apachegroovy:{ .lg .middle } `LineLength#length` | 100
    :material-language-kotlin:{ .lg .middle } `rulebook_max_line_length` | 100
    :material-language-c:{ .lg .middle }:material-language-cpp:{ .lg .middle } `--max-line-length` | 100
    :material-language-python:{ .lg .middle } `rulebook-max-line-length` | 100
    :material-language-javascript:{ .lg .middle } `max-len` | 100
    :material-language-typescript:{ .lg .middle } `max-len` | 100

### Unnecessary trailing space

A line should not end with a whitespace character.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    class AwesomeImplementation { /*...*/ } \n
    ```
=== "Groovy"

    ```groovy
    class AwesomeImplementation { /*...*/ } \n
    ```
=== "Kotlin"

    ```kotlin
    class AwesomeImplementation { /*...*/ } \n
    ```
=== "C/C++"

    ```cpp
    class AwesomeImplementation { /*...*/ } \n
    ```
=== "Python"

    ```python hl_lines="2"
    class AwesomeImplementation:
        pass \n
    ```
=== "JavaScript"

    ```js
    class AwesomeImplementation { /*...*/ } \n
    ```
=== "TypeScript"

    ```ts
    class AwesomeImplementation { /*...*/ } \n
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    class AwesomeImplementation { /*...*/ }\n
    ```
=== "Groovy"

    ```groovy
    class AwesomeImplementation { /*...*/ }\n
    ```
=== "Kotlin"

    ```kotlin
    class AwesomeImplementation { /*...*/ }\n
    ```
=== "C/C++"

    ```cpp
    class AwesomeImplementation { /*...*/ }\n
    ```
=== "Python"

    ```python hl_lines="2"
    class AwesomeImplementation:
        pass\n
    ```
=== "JavaScript"

    ```js
    class AwesomeImplementation { /*...*/ }\n
    ```
=== "TypeScript"

    ```ts
    class AwesomeImplementation { /*...*/ }\n
    ```

## Naming

### Abbreviation as word

Ensures that the first letter of acronyms longer than three characters are
always capitalized.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    class RestAPI {
        String httpURL = "https://example.com";
    }
    ```
=== "Groovy"

    ```groovy
    class RestAPI {
        var httpURL = 'https://example.com'
    }
    ```
=== "Kotlin"

    ```kotlin
    class RestAPI {
        val httpURL = "https://example.com"
    }
    ```
=== "C/C++"

    ```cpp
    class RestAPI {
        std::string httpURL = "https://example.com";
    }
    ```
=== "Python"

    ```python
    class RestAPI:
        http_url = 'https://example.com'
    ```
=== "TypeScript"

    ```ts
    class RestAPI {
        httpURL: string = 'https://example.com';
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    class RestApi {
        String httpUrl = "https://example.com";
    }
    ```
=== "Groovy"

    ```groovy
    class RestApi {
        var httpUrl = 'https://example.com'
    }
    ```
=== "Kotlin"

    ```kotlin
    class RestApi {
        val httpUrl = "https://example.com"
    }
    ```
=== "C/C++"

    ```cpp
    class RestApi {
        std::string http_url = "https://example.com";
    }
    ```
=== "Python"

    ```python
    class RestApi:
        http_url = 'https://example.com'
    ```
=== "TypeScript"

    ```ts
    class RestApi {
        httpUrl: string = 'https://example.com';
    }
    ```

### Boolean property interoperability

Kotlin field definitions that are Boolean types should be prefixed with `is`.
Otherwise, the compiler will generate a getter method with `get` prefix.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"

    ```kotlin
    val active: Boolean
    ```
    ```java
    boolean getActive() {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"

    ```kotlin
    val isActive: Boolean
    ```
    ```java
    boolean isActive() {}
    ```

### Class name

Class, interface and object names are written in `PascalCase`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    class train_station {}
    ```
=== "Groovy"

    ```groovy
    class train_station {}
    ```
=== "Kotlin"

    ```kotlin
    class train_station
    ```
=== "C/C++"

    ```cpp
    class train_station {};
    ```
=== "Python"

    ```python
    class train_station:
        pass
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    class TrainStation {}
    ```
=== "Groovy"

    ```groovy
    class TrainStation {}
    ```
=== "Kotlin"

    ```kotlin
    class TrainStation
    ```
=== "C/C++"

    ```cpp
    class TrainStation {};
    ```
=== "Python"

    ```python
    class TrainStation:
        pass
    ```

### Constant name

Constant fields should be written in `SCREAMING_SNAKE_CASE`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    static final int maxValue = 99;
    ```
=== "Groovy"

    ```groovy
    static final var maxValue = 99
    ```
=== "Kotlin"

    ```kotlin
    const val maxValue = 99
    ```
=== "TypeScript"

    ```ts
    const maxValue: number = 99;
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    static final int MAX_VALUE = 99;
    ```
=== "Groovy"

    ```groovy
    static final var MAX_VALUE = 99
    ```
=== "Kotlin"

    ```kotlin
    const val MAX_VALUE = 99
    ```
=== "TypeScript"

    ```ts
    const MAX_VALUE: number = 99;
    ```

### File name

If the file contains a single class, the file name should be the same as the
root class name.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```
    └─ com.example
       └─ UserObject.java
          └─ class User {}
    ```
=== "Groovy"

    ```
    └─ com.example
       └─ UserObject.groovy
          └─ class User {}
    ```
=== "Kotlin"

    ```
    └─ com.example
       └─ UserObject.kt
          └─ class User
    ```
=== "C/C++"

    ```
    └─ com.example
       └─ UserObject.cpp
          └─ class User {};
    ```
=== "Python"

    ```
    └─ com.example
       └─ user_object.py
          └─ class User:
              pass
    ```
=== "JavaScript"

    ```
    └─ com.example
       └─ UserObject.js
          └─ class User {}
    ```
=== "TypeScript"

    ```
    └─ com.example
       └─ UserObject.ts
          └─ class User {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```
    └─ com.example
       └─ User.java
          └─ class User {}
    ```
=== "Groovy"

    ```
    └─ com.example
       └─ User.groovy
          └─ class User {}
    ```
=== "Kotlin"

    ```
    └─ com.example
       └─ User.kt
          └─ class User
    ```
=== "C/C++"

    ```
    └─ com.example
       └─ User.cpp
          └─ class User {};
    ```
=== "Python"

    ```
    └─ com.example
       └─ user.py
          └─ class User:
              pass
    ```
=== "JavaScript"

    ```
    └─ com.example
       └─ User.js
          └─ class User {}
    ```
=== "TypeScript"

    ```
    └─ com.example
       └─ User.ts
          └─ class User {}
    ```

### Generic name

Generic type parameters should be named with a single uppercase letter. This
rule is ignored when there are multiple generic type parameters in the same
declaration.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    class Box<Element> {}

    void <Type> rotate(Box<Type> box) {}
    ```
=== "Groovy"

    ```groovy
    class Box<Element> {}

    void <Type> rotate(Box<Type> box) {}
    ```
=== "Kotlin"

    ```kotlin
    class Box<Element>() {}

    fun <Type> rotate(box: Box<Type>) {}
    ```
=== "C/C++"

    ```cpp
    template <typename Element>
    class Box {};

    template <typename Type>
    void rotate(Box<Type> box) {}
    ```
=== "Python"

    ```python
    from typing import TypeVar

    Element = TypeVar('Element')
    Type = TypeVar('Type')


    class Box(Element):
        pass


    def rotate(box: Box[Type]):
        pass
    ```
=== "TypeScript"

    ```ts
    class Box<Element> {}

    function rotate<Type>(box: Box<Type>): void {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    class Box<E> {}

    void <T> rotate(Box<T> box) {}
    ```
=== "Groovy"

    ```groovy
    class Box<E> {}

    void <T> rotate(Box<T> box) {}
    ```
=== "Kotlin"

    ```kotlin
    class Box<E>() {}

    fun <T> rotate(box: Box<T>) {}
    ```
=== "C/C++"

    ```cpp
    template <typename E>
    class Box {};

    template <typename T>
    void rotate(Box<T> box) {}
    ```
=== "Python"

    ```python
    from typing import TypeVar

    E = TypeVar('E')
    T = TypeVar('T')


    class Box(E):
        pass


    def rotate(box: Box[T]):
        pass
    ```
=== "TypeScript"

    ```ts
    class Box<E> {}

    function rotate<T>(box: Box<T>): void {}
    ```

### Identifier name

Non-constant fields, functions and parameters should be written in
**camelCase.** In Python and C/C++, the **snake_case** style is used instead.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    void DebugUser(User User) {
        User AnotherUser = User;
    }
    ```
=== "Groovy"

    ```groovy
    def DebugUser(User User) {
        var AnotherUser = User
    }
    ```
=== "Kotlin"

    ```kotlin
    fun DebugUser(User: User) {
        val AnotherUser = User
    }
    ```
=== "C/C++"

    ```cpp
    void DebugUser(User User) {
        User AnotherUser = User;
    }
    ```
=== "Python"

    ```python
    def DebugUser(User):
        AnotherUser = User
    ```
=== "TypeScript"

    ```ts
    function DebugUser(User: User): void {
        const AnotherUser: User = User;
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    void debugUser(User user) {
        User anotherUser = user;
    }
    ```
=== "Groovy"

    ```groovy
    def debugUser(User user) {
        var anotherUser = user
    }
    ```
=== "Kotlin"

    ```kotlin
    fun debugUser(user: User) {
        val anotherUser = user
    }
    ```
=== "C/C++"

    ```cpp
    void debug_user(User user) {
        User another_user = user;
    }
    ```
=== "Python"

    ```python
    def debug_user(user):
        another_user = user
    ```
=== "TypeScript"

    ```ts
    function debugUser(user: User): void {
        const anotherUser: User = user;
    }
    ```

### Illegal variable name

Prohibits primitive type names, base `Object` type names and their plural forms
as identifiers of properties, parameters and local variables. The name of the
identifier should be descriptive and meaningful.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    String string;

    List<Person> list;
    ```
=== "Groovy"

    ```groovy
    var string

    var list
    ```
=== "Kotlin"

    ```kotlin
    val string: String

    val list: List<Person>
    ```
=== "C/C++"

    ```cpp
    std::string string;

    std::vector<Person> list;
    ```
=== "Python"

    ```python
    string: str

    list: list[Person]
    ```
=== "JavaScript"

    ```js
    let string;

    let list;
    ```
=== "TypeScript"

    ```ts
    let string: string;

    let list: Person[];
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    String name;

    List<Person> people;
    ```
=== "Groovy"

    ```groovy
    var name

    var people
    ```
=== "Kotlin"

    ```kotlin
    val name: String

    val people: List<Person>
    ```
=== "C/C++"

    ```cpp
    std::string name;

    std::vector<Person> people;
    ```
=== "Python"

    ```python
    name: str

    people: list[Person]
    ```
=== "JavaScript"

    ```js
    let name;

    let people;
    ```
=== "TypeScript"

    ```ts
    let name: string;

    let people: Person[];
    ```

??? Configuration

    Setting | Default value
    --- | ---
    :material-language-java:{ .lg .middle } `IllegalIdentifierName#format` | object, integer, string, objects, integers, strings
    :simple-apachegroovy:{ .lg .middle } `IllegalVariableName#names` | object, integer, string, object, integers, strings
    :material-language-kotlin:{ .lg .middle } `rulebook_illegal_variable_names` | any, boolean, byte, char, double, float, int, long, short, string, many, booleans, bytes, chars, doubles, floats, ints, longs, shorts
    :material-language-c:{ .lg .middle }:material-language-cpp:{ .lg .middle } `--illegal-variable-names` | integer, string, integers, strings
    :material-language-python:{ .lg .middle } `bad-names` | objs, ints, strs
    :material-language-javascript:{ .lg .middle } `id-denylist` | error, object, number, string, objects, numbers, strings
    :material-language-typescript:{ .lg .middle } `id-denylist` | error, object, number, string, objects, numbers, strings

### Meaningless word

Prohibits meaningless source names in class, interface, object and files. The
name of utility classes (or files) should be the plural form of the extended
class.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    class SpaceshipWrapper {}
    ```
=== "Groovy"

    ```groovy
    class SpaceshipWrapper {}
    ```
=== "Kotlin"

    ```kotlin
    class SpaceshipWrapper
    ```
=== "C/C++"

    ```cpp
    class SpaceshipWrapper {};
    ```
=== "Python"

    ```python
    class SpaceshipWrapper():
        pass
    ```
=== "JavaScript"

    ```js
    class SpaceshipWrapper {}
    ```
=== "TypeScript"

    ```ts
    class SpaceshipWrapper {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    class Spaceship {}
    ```
=== "Groovy"

    ```groovy
    class Spaceship {}
    ```
=== "Kotlin"

    ```kotlin
    class Spaceship
    ```
=== "C/C++"

    ```cpp
    class Spaceship {};
    ```
=== "Python"

    ```python
    class Spaceship():
        pass
    ```
=== "JavaScript"

    ```js
    class Spaceship {}
    ```
=== "TypeScript"

    ```ts
    class Spaceship {}
    ```

??? Configuration

    Setting | Default value
    --- | ---
    :material-language-java:{ .lg .middle } `MeaninglessWord#words` | Util, Utility, Helper, Manager, Wrapper
    :simple-apachegroovy:{ .lg .middle } `MeaninglessWord#words` | Util, Utility, Helper, Manager, Wrapper
    :material-language-kotlin:{ .lg .middle } `rulebook_meaningless_words` | Util, Utility, Helper, Manager, Wrapper
    :material-language-c:{ .lg .middle }:material-language-cpp:{ .lg .middle } `--meaningless-words` | Util, Utility, Helper, Manager, Wrapper
    :material-language-python:{ .lg .middle } `rulebook-meaningless-words` | Util, Utility, Helper, Manager, Wrapper

### Package name

Package names should be written in lowercase with no separators. In C++,
namespaces are considered as packages.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    package com.example.user_management;
    ```
=== "Groovy"

    ```groovy
    package com.example.user_management
    ```
=== "Kotlin"

    ```kotlin
    package com.example.user_management
    ```
=== "C/C++"

    ```cpp
    namespace com {
        namespace example {
            namespace user_management {
                // ...
            }
        }
    }
    ```
=== "Python"

    ```
    └─ UserManagement
       └─ user_config.py
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    package com.example.usermanagement;
    ```
=== "Groovy"

    ```groovy
    package com.example.usermanagement
    ```
=== "Kotlin"

    ```kotlin
    package com.example.usermanagement
    ```
=== "C/C++"

    ```cpp
    namespace com {
        namespace example {
            namespace usermanagement {
                // ...
            }
        }
    }
    ```
=== "Python"

    ```python
    └─ user_management
       └─ user_config.py
    ```

## Ordering

### Block tag order

Block tags should be ordered in the following sequence: `@constructor`,
`@receiver`, `@param`, `@property`, `@return`, `@throws`, `@see`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2-4"
    /**
     * @see User
     * @return The user object.
     * @param name The name of the user.
     */
    abstract User createUser(String name);
    ```
=== "Groovy"

    ```groovy hl_lines="2-4"
    /**
     * @see User
     * @return The user object.
     * @param name The name of the user.
     */
    abstract def createUser(String name)
    ```
=== "Kotlin"

    ```kotlin hl_lines="2-4"
    /**
     * @see User
     * @return The user object.
     * @param name The name of the user.
     */
    abstract fun createUser(name: String): User
    ```
=== "JavaScript"

    ```js hl_lines="2-4"
    /**
     * @see User
     * @return The user object.
     * @param name The name of the user.
     */
    function createUser(name) {}
    ```
=== "TypeScript"

    ```ts hl_lines="2-4"
    /**
     * @see User
     * @return The user object.
     * @param name The name of the user.
     */
    function createUser(name: string): User {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="2-4"
    /**
     * @param name The name of the user.
     * @return The user object.
     * @see User
     */
    abstract User createUser(String name);
    ```
=== "Groovy"

    ```groovy hl_lines="2-4"
    /**
     * @param name The name of the user.
     * @return The user object.
     * @see User
     */
    abstract def createUser(String name)
    ```
=== "Kotlin"

    ```kotlin hl_lines="2-4"
    /**
     * @param name The name of the user.
     * @return The user object.
     * @see User
     */
    abstract fun createUser(name: String): User
    ```
=== "JavaScript"

    ```js hl_lines="2-4"
    /**
     * @param name The name of the user.
     * @return The user object.
     * @see User
     */
    function createUser(name) {}
    ```
=== "TypeScript"

    ```ts hl_lines="2-4"
    /**
     * @param name The name of the user.
     * @return The user object.
     * @see User
     */
    function createUser(name: string): User {}
    ```

### Common function position

Place Object built-in methods such as `toString()`, `hashCode()` and `equals()`
at the end of the class.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="8-11"
    class Notification {
        private String message;

        public Notification(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }

        public void notify() {
            return System.out.println(message);
        }
    }
    ```
=== "Groovy"

    ```groovy hl_lines="8-11"
    class Notification {
        private var message

        public Notification(String message) {
            this.message = message
        }

        @Override
        public def toString() {
            return message
        }

        public def notify() {
            println(message)
        }
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="2"
    class Notification(private val message: String) {
        public override fun toString(): String = message

        public fun notify() {
            println(message)
        }
    }
    ```
=== "Python"

    ```python hl_lines="6-7"
    class Notification:
        def __init__(self, message):
            self.message = message
            self.id = randomize()

        def __str__(self):
            return self.message

        def notify(self):
            print(self.message)
    ```
=== "JavaScript"

    ```js hl_lines="6-8"
    class Notification {
        constructor(message) {
            this.message = message;
        }

        toString() {
            return this.message;
        }

        notify() {
            console.log(this.message);
        }
    }
    ```
=== "TypeScript"

    ```ts hl_lines="6-8"
    class Notification {
        constructor(message: string) {
            this.message = message;
        }

        toString(): string {
            return this.message;
        }

        notify(): void {
            console.log(this.message);
        }
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="12-15"
    class Notification {
        private String message;

        public Notification(String message) {
            this.message = message;
        }

        public void notify() {
            return System.out.println(message);
        }

        @Override
        public String toString() {
            return message;
        }
    }
    ```
=== "Groovy"

    ```groovy hl_lines="12-15"
    class Notification {
        private var message

        public Notification(String message) {
            this.message = message
        }

        public def notify() {
            println(message)
        }

        @Override
        public def toString() {
            return message
        }
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="6"
    class Notification(private val message: String) {
        public fun notify() {
            println(message)
        }

        public override fun toString(): String = message
    }
    ```
=== "Python"

    ```python hl_lines="9-10"
    class Notification:
        def __init__(self, message):
            self.message = message
            self.id = randomize()

        def notify(self):
            print(self.message)

        def __str__(self):
            return self.message
    ```
=== "JavaScript"

    ```js hl_lines="10-12"
    class Notification {
        constructor(message) {
            this.message = message;
        }

        notify() {
            console.log(this.message);
        }

        toString() {
            return this.message;
        }
    }
    ```
=== "TypeScript"

    ```ts hl_lines="10-12"
    class Notification {
        constructor(message: string) {
            this.message = message;
        }

        notify(): void {
            console.log(this.message);
        }

        toString(): string {
            return this.message;
        }
    }
    ```

### Import order

Import directives should be ordered alphabetically without any blank lines.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    import java.util.List;

    import com.example.User;
    ```
=== "Groovy"

    ```groovy
    import java.util.List

    import com.example.User
    ```
=== "Kotlin"

    ```kotlin
    import java.util.List

    import com.example.User
    ```
=== "C/C++"

    ```cpp
    #include "user.h"

    #include <vector>
    ```
=== "Python"

    ```python
    import utils

    import user
    ```
=== "JavaScript"

    ```js
    import { Utils } from './utils';

    import { User } from './user';
    ```
=== "TypeScript"

    ```ts
    import { Utils } from './utils';

    import { User } from './user';
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    import com.example.User;
    import java.util.List;
    ```
=== "Groovy"

    ```groovy
    import com.example.User
    import java.util.List
    ```
=== "Kotlin"

    ```kotlin
    import com.example.User
    import java.util.List
    ```
=== "C/C++"

    ```cpp
    #include <vector>
    #include "user.h"
    ```
=== "Python"

    ```python
    import user
    import utils
    ```
=== "JavaScript"

    ```js
    import { User } from './user';
    import { Utils } from './utils';
    ```
=== "TypeScript"

    ```ts
    import { User } from './user';
    import { Utils } from './utils';
    ```

### Inner class position

Place inner classes at the end of the class.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2"
    class Article {
        class Author {}

        Article(String content, Author author) {}

        Article(String content) {}
    }
    ```
=== "Groovy"

    ```groovy hl_lines="2"
    class Article {
        class Author {}

        Article(String content, Author author) {}

        Article(String content) {}
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="2"
    class Article(content: String, author: Author) {
        class Author(name: String)

        constructor(content: String) : this(content, null)
    }
    ```
=== "C/C++"

    ```cpp hl_lines="2"
    class Article {
        class Author {};

        Article(std::string content, Author author) {}

        Article(std::string content) {}
    }
    ```
=== "Python"

    ```python hl_lines="2"
    class Article:
        class Author:
            pass

        def __init__(self, content, author = None):
            pass
    ```
=== "JavaScript"

    ```js hl_lines="2"
    class Article {
        class Author {}

        constructor(content, author) {}

        constructor(content) {}
    }
    ```
=== "TypeScript"

    ```ts hl_lines="2"
    class Article {
        class Author {}

        constructor(content: string, author: Author) {}

        constructor(content: string) {}
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="6"
    class Article {
        Article(String content, Author author) {}

        Article(String content) {}

        class Author {}
    }
    ```
=== "Groovy"

    ```groovy hl_lines="6"
    class Article {
        Article(String content, Author author) {}

        Article(String content) {}

        class Author {}
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="4"
    class Article(content: String, author: Author) {
        constructor(content: String) : this(content, null)

        class Author(name: String)
    }
    ```
=== "C/C++"

    ```cpp hl_lines="6"
    class Article {
        Article(std::string content, Author author) {}

        Article(std::string content) {}

        class Author {};
    }
    ```
=== "Python"

    ```python hl_lines="5"
    class Article:
        def __init__(self, content, author = None):
            pass

        class Author:
            pass
    ```
=== "JavaScript"

    ```js hl_lines="6"
    class Article {
        constructor(content, author) {}

        constructor(content) {}

        class Author {}
    }
    ```
=== "TypeScript"

    ```ts hl_lines="6"
    class Article {
        constructor(content: string, author: Author) {}

        constructor(content: string) {}

        class Author {}
    }
    ```

### Member order

The class should be organized as follows: properties, initializer block,
constructors, methods and static members. Inner classes should be declared next
to the last member that uses them.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    class Car {
        static void log(String message) {
            System.out.println(message);
        }

        Car(String brand, String model) {}

        Car(String brand) {
            this(brand, "Unknown");
        }

        int wheels = 4;

        void start() {
            log("Car created");
        }
    }
    ```
=== "Groovy"

    ```groovy
    class Car {
        static def log(String message) {
            System.out.println(message)
        }

        Car(String brand, String model) {}

        Car(String brand) {
            this(brand, 'Unknown')
        }

        var wheels = 4

        def start() {
            log('Car created')
        }
    }
    ```
=== "Kotlin"

    ```kotlin
    class Car(brand: String, model: String) {
        companion object {
            fun log(message: String) {
                println(message)
            }
        }

        init {
            log("Car created")
        }

        constructor(brand: String) : this(brand, "Unknown")

        val wheels = 4

        fun start() {
            log("Car started")
        }
    }
    ```
=== "C/C++"

    ```cpp
    class Car {
        static void log(std::string message) {
            std::cout << message << std::endl;
        }

        Car(std::string brand, std::string model) {}

        Car(std::string brand) : Car(brand, "Unknown") {}

        int wheels = 4;

        void start() {
            log("Car created");
        }
    }
    ```
=== "Python"

    ```python
    class Car:
        @staticmethod
        def log(message: str):
            print(message)

        def __init__(self, brand, model = 'Unknown'):
            pass

        wheels = 4

        def start(self):
            log('Car started')
    ```
=== "JavaScript"

    ```js
    class Car {
        static log(message) {
            console.log(message);
        }

        constructor(brand, model) {}

        constructor(brand) {
            this(brand, 'Unknown');
        }

        wheels = 4;

        start() {
            Car.log('Car created');
        }
    }
    ```
=== "TypeScript"

    ```ts
    class Car {
        static log(message: string): void {
            console.log(message);
        }

        constructor(brand: string, model: string) {}

        constructor(brand: string) {
            this(brand, 'Unknown');
        }

        wheels: number = 4;

        start(): void {
            Car.log('Car created');
        }
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    class Car {
        int wheels = 4;

        Car(String brand, String model) {}

        Car(String brand) {
            this(brand, "Unknown");
        }

        void start() {
            log("Car created");
        }

        static void log(String message) {
            System.out.println(message);
        }
    }
    ```
=== "Groovy"

    ```groovy
    class Car {
        var wheels = 4

        Car(String brand, String model) {}

        Car(String brand) {
            this(brand, 'Unknown')
        }

        def start() {
            log('Car created')
        }

        static def log(String message) {
            System.out.println(message)
        }
    }
    ```
=== "Kotlin"

    ```kotlin
    class Car(brand: String, model: String) {
        override val wheels = 4

        init {
            log("Car created")
        }

        constructor(brand: String) : this(brand, "Unknown")

        fun start() {
            log("Car started")
        }

        companion object {
            fun log(message: String) {
                println(message)
            }
        }
    }
    ```
=== "C/C++"

    ```cpp
    class Car {
        int wheels = 4;

        Car(std::string brand, std::string model) {}

        Car(std::string brand) : Car(brand, "Unknown") {}

        void start() {
            log("Car created");
        }

        static void log(std::string message) {
            std::cout << message << std::endl;
        }
    }
    ```
=== "Python"

    ```python
    class Car:
        wheels = 4

        def __init__(self, brand, model = 'Unknown'):
            pass

        def start(self):
            log('Car started')

        @staticmethod
        def log(message: str):
            print(message)
    ```
=== "JavaScript"

    ```js
    class Car {
        wheels = 4;

        constructor(brand, model) {}

        constructor(brand) {
            this(brand, 'Unknown');
        }

        start() {
            Car.log('Car created');
        }

        static log(message) {
            console.log(message);
        }
    }
    ```
=== "TypeScript"

    ```ts
    class Car {
        wheels: number = 4;

        constructor(brand: string, model: string) {}

        constructor(brand: string) {
            this(brand, 'Unknown');
        }

        start(): void {
            Car.log('Car created');
        }

        static log(message: string): void {
            console.log(message);
        }
    }
    ```

??? Configuration

    Setting | Default value
    --- | ---
    :material-language-java:{ .lg .middle } `MemberOrder#order` | property, constructor, function, static
    :simple-apachegroovy:{ .lg .middle } `MemberOrder#order` | property, constructor, function, static
    :material-language-kotlin:{ .lg .middle } `rulebook_member_order` | property, initializer, constructor, function, companion
    :material-language-c:{ .lg .middle }:material-language-cpp:{ .lg .middle } `--member-order` | property, constructor, function, static
    :material-language-python:{ .lg .middle } `rulebook-member-order` | property, constructor, function, static
    :material-language-javascript:{ .lg .middle } `sort-class-members` | property, constructor, function, static
    :material-language-typescript:{ .lg .middle } `sort-class-members` | property, constructor, function, static

### Modifier order

Visibility modifiers first, followed by abstraction modifiers, then other
modifiers.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    abstract public class Beacon {
        final static int MAX_RANGE = 100;
    }
    ```
=== "Groovy"

    ```groovy
    abstract public class Beacon {
        final static var MAX_RANGE = 100
    }
    ```
=== "Kotlin"

    ```kotlin
    abstract public class Beacon {
        companion object {
            const val MAX_RANGE = 100
        }
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    public abstract class Beacon {
        static final int MAX_RANGE = 100;
    }
    ```
=== "Groovy"

    ```groovy
    public abstract class Beacon {
        static final var MAX_RANGE = 100
    }
    ```
=== "Kotlin"

    ```kotlin
    public abstract class Beacon {
        companion object {
            const val MAX_RANGE = 100
        }
    }
    ```

### Named import order

Multiple import directives from the same package should be ordered
alphabetically.

**:material-star-four-points-outline:{ #accent } Before**

=== "Python"

    ```python
    from utils import validate, parse, format
    ```
=== "JavaScript"

    ```js
    import { validate, parse, format } from './utils';
    ```
=== "TypeScript"

    ```ts
    import { validate, parse, format } from './utils';
    ```

**:material-star-four-points:{ #accent } After**

=== "Python"

    ```python
    from utils import format, parse, validate
    ```
=== "JavaScript"

    ```js
    import { format, parse, validate } from './utils';
    ```
=== "TypeScript"

    ```ts
    import { format, parse, validate } from './utils';
    ```

### Overload function position

Place overloaded functions next to each other.

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="9-11"
    int sum(int a, int b) {
        return a + b;
    }

    int times(int a, int b) {
        return a * b;
    }

    int sum(int a, int b, int c) {
        return a + b + c;
    }
    ```
=== "Groovy"

    ```groovy hl_lines="9-11"
    def sum(int a, int b) {
        return a + b
    }

    def times(int a, int b) {
        return a * b
    }

    def sum(int a, int b, int c) {
        return a + b + c
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="5"
    fun sum(a: Int, b: Int): Int = a + b

    fun times(a: Int, b: Int): Int = a * b

    fun sum(a: Int, b: Int, c: Int): Int = a + b + c
    ```
=== "JavaScript"

    ```js hl_lines="9-11"
    function sum(a, b) {
        return a + b;
    }

    function times(a, b) {
        return a * b;
    }

    function sum(a, b, c) {
        return a + b + c;
    }
    ```
=== "TypeScript"

    ```ts hl_lines="9-11"
    function sum(a: number, b: number): number {
        return a + b;
    }

    function times(a: number, b: number): number {
        return a * b;
    }

    function sum(a: number, b: number, c: number): number {
        return a + b + c;
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="5-7"
    int sum(int a, int b) {
        return a + b;
    }

    int sum(int a, int b, int c) {
        return a + b + c;
    }

    int times(int a, int b) {
        return a * b;
    }
    ```
=== "Groovy"

    ```groovy hl_lines="5-7"
    def sum(int a, int b) {
        return a + b
    }

    def sum(int a, int b, int c) {
        return a + b + c
    }

    def times(int a, int b) {
        return a * b
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="3"
    fun sum(a: Int, b: Int): Int = a + b

    fun sum(a: Int, b: Int, c: Int): Int = a + b + c

    fun times(a: Int, b: Int): Int = a * b
    ```
=== "JavaScript"

    ```js hl_lines="5-7"
    function sum(a, b) {
        return a + b;
    }

    function sum(a, b, c) {
        return a + b + c;
    }

    function times(a, b) {
        return a * b;
    }
    ```
=== "TypeScript"

    ```ts hl_lines="5-7"
    function sum(a: number, b: number): number {
        return a + b;
    }

    function sum(a: number, b: number, c: number): number {
        return a + b + c;
    }

    function times(a: number, b: number): number {
        return a * b;
    }
    ```

### Static import position

Static import directives are to be placed before non-static imports, separated
by a blank line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="3"
    import java.util.List;

    import static java.lang.Math.PI;
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    import java.util.List

    import static java.lang.Math.PI
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="1"
    import static java.lang.Math.PI;

    import java.util.List;
    ```
=== "Groovy"

    ```groovy hl_lines="1"
    import static java.lang.Math.PI

    import java.util.List
    ```

## Scripting

### Decentralized dependency

Declare dependencies in a centralizeed version catalog file and refer to them by
their alias in the build script.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"

    ```groovy
    dependencies.implementation 'org.apache.commons:commons-lang3:3.12.0'
    ```
=== "Kotlin"

    ```kotlin
    dependencies.implementation("org.apache.commons:commons-lang3:3.12.0")
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"

    ```groovy
    dependencies.implementation libs.commons.lang3
    ```
=== "Kotlin"

    ```kotlin
    dependencies.implementation(libs.commons.lang3)
    ```

### Eager API

Prefer eager calls over lazy ones.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"

    ```groovy hl_lines="1"
    tasks.findByName('compileJava') {
        println 'Compilation completed'
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="1"
    tasks.findByName("compileJava") {
        println("Compilation completed")
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"

    ```groovy hl_lines="1"
    tasks.named('compileJava') {
        println 'Compilation completed'
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="1"
    tasks.named("compileJava") {
        println("Compilation completed")
    }
    ```

### Lonely configuration

Avoid opening a scope for a single configuration.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"

    ```groovy
    repositories {
        mavenCentral()
    }
    ```
=== "Kotlin"

    ```kotlin
    repositories {
        mavenCentral()
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"

    ```groovy
    repositories.mavenCentral()
    ```
=== "Kotlin"

    ```kotlin
    repositories.mavenCentral()
    ```

### Root project name

Specify the root project name in `settings.gradle` or `settings.gradle.kts`.

**:material-star-four-points-outline:{ #accent } After**

=== "Groovy"

    ```groovy
    rootProject.name = 'my-app'
    ```
=== "Kotlin"

    ```kotlin
    rootProject.name = "my-app"
    ```

### Script file name

Script file names are in lowercase with words separated by hyphens.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"

    ```
    └─ publish_site.gradle
    ```
=== "Kotlin"

    ```
    └─ publish_site.gradle.kts
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"

    ```
    └─ publish-site.gradle
    ```
=== "Kotlin"

    ```
    └─ publish-site.gradle.kts
    ```

## Spacing

### Block comment spaces

Ensures that block comments starts and ends with a whitespace. In multiline
comments, each line after the asterisk should be indented by a whitespace.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="1"
    /**Pass on user behavior.*/
    void report() {}
    ```
=== "Groovy"

    ```groovy hl_lines="1"
    /**Pass on user behavior.*/
    def report() {}
    ```
=== "Kotlin"

    ```kotlin hl_lines="1"
    /**Pass on user behavior.*/
    fun report()
    ```
=== "C/C++"

    ```cpp hl_lines="1"
    /**Pass on user behavior.*/
    void report() {}
    ```
=== "JavaScript"

    ```js hl_lines="1"
    /**Pass on user behavior.*/
    function report() {}
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    /**Pass on user behavior.*/
    function report(): void {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="1"
    /** Pass on user behavior. */
    void report() {}
    ```
=== "Groovy"

    ```groovy hl_lines="1"
    /** Pass on user behavior. */
    def report() {}
    ```
=== "Kotlin"

    ```kotlin hl_lines="1"
    /** Pass on user behavior. */
    fun report()
    ```
=== "C/C++"

    ```cpp hl_lines="1"
    /** Pass on user behavior. */
    void report() {}
    ```
=== "JavaScript"

    ```js hl_lines="1"
    /** Pass on user behavior. */
    function report() {}
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    /** Pass on user behavior. */
    function report(): void {}
    ```

### Block tag indentation

Multi-line block tag descriptions should be indented by four spaces, or five
spaces from the leading asterisk.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="3"
    /**
     * @param num the number to return
     * the absolute value for.
     */
    void abs(int num) {}
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    /**
     * @param num the number to return
     * the absolute value for.
     */
    def abs(int num) {}
    ```
=== "Kotlin"

    ```kotlin hl_lines="3"
    /**
     * @param num the number to return
     * the absolute value for.
     */
    fun abs(num: Int): Int
    ```
=== "C/C++"

    ```cpp hl_lines="3"
    /**
     * @param num the number to return
     * the absolute value for.
     */
    void abs(int num) {}
    ```
=== "JavaScript"

    ```js hl_lines="3"
    /**
     * @param num the number to return
     * the absolute value for.
     */
    function abs(num) {}
    ```
=== "TypeScript"

    ```ts hl_lines="3"
    /**
     * @param num the number to return
     * the absolute value for.
     */
    function abs(num: number): number {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="3"
    /**
     * @param num the number to return
     *     the absolute value for.
     */
    void abs(int num) {}
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    /**
     * @param num the number to return
     *     the absolute value for.
     */
    def abs(int num) {}
    ```
=== "Kotlin"

    ```kotlin hl_lines="3"
    /**
     * @param num the number to return
     *     the absolute value for.
     */
    fun abs(num: Int): Int
    ```
=== "C/C++"

    ```cpp hl_lines="3"
    /**
     * @param num the number to return
     *     the absolute value for.
     */
    void abs(int num) {}
    ```
=== "JavaScript"

    ```js hl_lines="3"
    /**
     * @param num the number to return
     *     the absolute value for.
     */
    function abs(num) {}
    ```
=== "TypeScript"

    ```ts hl_lines="3"
    /**
     * @param num the number to return
     *     the absolute value for.
     */
    function abs(num: number): number {}
    ```

### Case separator

Multiline switch-case entries end with a blank line while short entries are
joined.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="8"
    switch (event) {
        case CANCELLED:
            return;

        case PAST:
            String message = "Event is in the past";
            throw new IllegalStateException(message);
        default:
            createEvent(event);
    }
    ```
=== "Groovy"

    ```groovy hl_lines="8"
    switch (event) {
        case CANCELLED:
            return

        case PAST:
            var message = 'Event is in the past'
            throw new IllegalStateException(message)
        default:
            createEvent(event)
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="8"
    when {
        event.isCancelled() -> return

        event.date < now -> {
            val message = "Event is in the past"
            throw IllegalStateException(message)
        }
        else -> createEvent(event)
    }
    ```
=== "C/C++"

    ```cpp hl_lines="9"
    switch (event) {
        case CANCELLED:
            return;

        case PAST: {
            std::string message = "Event is in the past";
            throw std::logic_error(message);
        }
        default:
            create_event(event);
    }
    ```
=== "Python"

    ```python hl_lines="8"
    match event:
        case CANCELLED:
            return

        case PAST:
            message = 'Event is in the past'
            raise ValueError(message)
        case _:
            create_event(event)
    ```
=== "JavaScript"

    ```js hl_lines="9"
    switch (event) {
        case CANCELLED:
            return;

        case PAST: {
            const message = 'Event is in the past';
            throw new Error(message);
        }
        default:
            createEvent(event);
    }
    ```
=== "TypeScript"

    ```ts hl_lines="9"
    switch (event) {
        case CANCELLED:
            return;

        case PAST: {
            const message = 'Event is in the past';
            throw new Error(message);
        }
        default:
            createEvent(event);
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    switch (event) {
        case CANCELLED:
            return;

        case PAST:
            String message = "Event is in the past";
            throw new IllegalStateException(message);

        default:
            createEvent(event);
    }
    ```
=== "Groovy"

    ```groovy
    switch (event) {
        case CANCELLED:
            return

        case PAST:
            var message = 'Event is in the past'
            throw new IllegalStateException(message)

        default:
            createEvent(event)
    }
    ```
=== "Kotlin"

    ```kotlin
    when {
        event.isCancelled() -> return

        event.date < now -> {
            val message = "Event is in the past"
            throw IllegalStateException(message)
        }

        else -> createEvent(event)
    }
    ```
=== "C/C++"

    ```cpp
    switch (event) {
        case CANCELLED:
            return;

        case PAST: {
            std::string message = "Event is in the past";
            throw std::logic_error(message);
        }

        default:
            create_event(event);
    }
    ```
=== "Python"

    ```python
    match event:
        case CANCELLED:
            return

        case PAST:
            message = 'Event is in the past'
            raise ValueError(message)

        case _:
            create_event(event)
    ```
=== "JavaScript"

    ```js
    switch (event) {
        case CANCELLED:
            return;

        case PAST: {
            const message = 'Event is in the past';
            throw new Error(message);
        }

        default:
            createEvent(event);
    }
    ```
=== "TypeScript"

    ```ts
    switch (event) {
        case CANCELLED:
            return;

        case PAST: {
            const message = 'Event is in the past';
            throw new Error(message);
        }

        default:
            createEvent(event);
    }
    ```

### Comment spaces

End-of-file comments should be separated by a single whitespace from the
preceding code, and start with a single whitespace.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    System.out.println("This is a code");//This is a comment
    ```
=== "Groovy"

    ```groovy
    println('This is a code')//This is a comment
    ```
=== "Kotlin"

    ```kotlin
    println("This is a code")//This is a comment
    ```
=== "C/C++"

    ```cpp
    std::cout << "This is a code" << std::endl;//This is a comment
    ```
=== "Python"

    ```python
    print('This is a code')#This is a comment
    ```
=== "JavaScript"

    ```js
    console.log('This is a code');//This is a comment
    ```
=== "TypeScript"

    ```ts
    console.log('This is a code');//This is a comment
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    System.out.println("This is a code"); // This is a comment
    ```
=== "Groovy"

    ```groovy
    println('This is a code') // This is a comment
    ```
=== "Kotlin"

    ```kotlin
    println("This is a code") // This is a comment
    ```
=== "C/C++"

    ```cpp
    std::cout << "This is a code" << std::endl; // This is a comment
    ```
=== "Python"

    ```python
    print('This is a code')  # This is a comment
    ```
=== "JavaScript"

    ```js
    console.log('This is a code'); // This is a comment
    ```
=== "TypeScript"

    ```ts
    console.log('This is a code'); // This is a comment
    ```

!!! warning

    PEP8 requires leading two spaces for comments.

### Member separator

Class, function and property declarations should be separated by a blank line.
There is an exception for a group of properties.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    interface Vehicle {
        int getWheels();
        void start();
    }
    ```
=== "Groovy"

    ```groovy
    interface Vehicle {
        def getWheels();
        def start();
    }
    ```
=== "Kotlin"

    ```kotlin
    interface Vehicle {
        val wheels: Int
        fun start()
    }
    ```
=== "C/C++"

    ```cpp
    class Vehicle {
        int get_wheels();
        void start();
    }
    ```
=== "Python"

    ```python
    class Vehicle:
        wheels: int
        def start(self):
            pass
    ```
=== "JavaScript"

    ```js
    class Vehicle {
        getWheels() {}
        start() {}
    }
    ```
=== "TypeScript"

    ```ts
    class Vehicle {
        getWheels(): number {}
        start(): void {}
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="3"
    interface Vehicle {
        int getWheels();

        void start();
    }
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    interface Vehicle {
        def getWheels();

        void start();
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="3"
    interface Vehicle {
        val wheels: Int

        fun start()
    }
    ```
=== "C/C++"

    ```cpp hl_lines="3"
    class Vehicle {
        int get_wheels();

        void start();
    }
    ```
=== "Python"

    ```python hl_lines="3"
    class Vehicle:
        wheels: int

        def start(self):
            pass
    ```
=== "JavaScript"

    ```js hl_lines="3"
    class Vehicle {
        getWheels() {}

        start() {}
    }
    ```
=== "TypeScript"

    ```ts hl_lines="3"
    class Vehicle {
        getWheels(): number {}

        start(): void {}
    }
    ```

### Missing blank line before block tags

Separate block tag group from the summary with a blank line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    /**
     * Returns the absolute value of the given number.
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    void abs(int number) {}
    ```
=== "Groovy"

    ```groovy
    /**
     * Returns the absolute value of the given number.
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    def abs(int number) {}
    ```
=== "Kotlin"

    ```kotlin
    /**
     * Returns the absolute value of the given number.
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    fun abs(number: Int): Int
    ```
=== "JavaScript"

    ```js
    /**
     * Returns the absolute value of the given number.
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    function abs(number) {}
    ```
=== "TypeScript"

    ```ts
    /**
     * Returns the absolute value of the given number.
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    function abs(number: number): number {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="3"
    /**
     * Returns the absolute value of the given number.
     *
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    void abs(int number) {}
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    /**
     * Returns the absolute value of the given number.
     *
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    def abs(int number) {}
    ```
=== "Kotlin"

    ```kotlin hl_lines="3"
    /**
     * Returns the absolute value of the given number.
     *
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    fun abs(number: Int): Int
    ```
=== "JavaScript"

    ```js hl_lines="3"
    /**
     * Returns the absolute value of the given number.
     *
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    function abs(number) {}
    ```
=== "TypeScript"

    ```ts hl_lines="3"
    /**
     * Returns the absolute value of the given number.
     *
     * @param number The number to return the absolute value for.
     * @return The absolute value.
     */
    function abs(number: number): number {}
    ```

## Stating

### Complicated assignment

Use shorthand assignment operators when the variable being assigned is also used
in the expression.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2"
    int count = 0;
    count = count + 1;
    ```
=== "Groovy"

    ```groovy hl_lines="2"
    var count = 0
    count = count + 1
    ```
=== "Kotlin"

    ```kotlin hl_lines="2"
    var count = 0
    count = count + 1
    ```
=== "C/C++"

    ```cpp hl_lines="2"
    int count = 0;
    count = count + 1;
    ```
=== "Python"

    ```python hl_lines="2"
    count = 0
    count = count + 1
    ```
=== "JavaScript"

    ```js hl_lines="2"
    let count = 0;
    count = count + 1;
    ```
=== "TypeScript"

    ```ts hl_lines="2"
    let count: number = 0;
    count = count + 1;
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="2"
    int count = 0;
    count += 1;
    ```
=== "Groovy"

    ```groovy hl_lines="2"
    var count = 0
    count += 1
    ```
=== "Kotlin"

    ```kotlin hl_lines="2"
    var count = 0
    count += 1
    ```
=== "C/C++"

    ```cpp hl_lines="2"
    int count = 0;
    count += 1;
    ```
=== "Python"

    ```python hl_lines="2"
    count = 0
    count += 1
    ```
=== "JavaScript"

    ```js hl_lines="2"
    let count = 0;
    count += 1;
    ```
=== "TypeScript"

    ```ts hl_lines="2"
    let count: number = 0;
    count += 1;
    ```

### Illegal catch

Catch specific exception subclass instead of the generic `Throwable`,
`Exception` or `Error`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="3"
    try {
        unsafeOperation();
    } catch (Throwable e) {
        e.printStackTrace();
    }
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    try {
        unsafeOperation()
    } catch (Throwable e) {
        e.printStackTrace()
    }
    ```
=== "C/C++"

    ```cpp hl_lines="3"
    try {
        unsafe_operation();
    } catch (std::exception& e) {
        std::cerr << e.what() << std::endl;
    }
    ```
=== "Python"

    ```python hl_lines="3"
    try:
        unsafe_operation()
    except Exception as e:
        print(e)
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="3"
    try {
        unsafeOperation();
    } catch (IOException | SQLException e) {
        e.printStackTrace();
    }
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    try {
        unsafeOperation()
    } catch (IOException | SQLException e) {
        e.printStackTrace()
    }
    ```
=== "C/C++"

    ```cpp hl_lines="3"
    try {
        unsafe_operation();
    } catch (std::ios_base::failure& e) {
        std::cerr << e.what() << std::endl;
    }
    ```
=== "Python"

    ```python hl_lines="3"
    try:
        unsafe_operation()
    except (IOError, OSError) as e:
        print(e)
    ```

### Illegal throw

Throw a narrower exception type instead of `Exception`, `Error` or `Throwable`.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    throw new Exception();
    ```
=== "Groovy"

    ```groovy
    throw new Exception()
    ```
=== "Kotlin"

    ```kotlin
    throw Exception()
    ```
=== "C/C++"

    ```cpp
    throw std::exception();
    ```
=== "Python"

    ```python
    raise Exception()
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    throw new IllegalStateException();
    ```
=== "Groovy"

    ```groovy
    throw new IllegalStateException()
    ```
=== "Kotlin"

    ```kotlin
    throw IllegalStateException()
    ```
=== "C/C++"

    ```cpp
    throw std::logic_error("Illegal state");
    ```
=== "Python"

    ```python
    raise ValueError()
    ```

### Lonely case

If a switch statement has single branch, it should be replaced with an if
statement.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    switch (token) {
        case VALUE_TOKEN:
            callback(token);
    }
    ```
=== "Groovy"

    ```groovy
    switch (token) {
        case VALUE_TOKEN:
            callback(token)
    }
    ```
=== "Kotlin"

    ```kotlin
    when (token) {
        is Token.ValueToken -> callback(token)
    }
    ```
=== "C/C++"

    ```cpp
    switch (token) {
        case VALUE_TOKEN:
            callback(token);
    }
    ```
=== "Python"

    ```python
    match token:
        case Token.VALUE_TOKEN:
            callback(token)
    ```
=== "JavaScript"

    ```js
    switch (token) {
        case VALUE_TOKEN:
            callback(token);
    }
    ```
=== "TypeScript"

    ```ts
    switch (token) {
        case VALUE_TOKEN:
            callback(token);
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    if (token == Token.VALUE_TOKEN) {
        callback(token);
    }
    ```
=== "Groovy"

    ```groovy
    if (token == Token.VALUE_TOKEN) {
        callback(token)
    }
    ```
=== "Kotlin"

    ```kotlin
    if (token is Token.ValueToken) {
        callback(token)
    }
    ```
=== "C/C++"

    ```cpp
    if (token == VALUE_TOKEN) {
        callback(token);
    }
    ```
=== "Python"

    ```python
    if token == Token.VALUE_TOKEN:
        callback(token)
    ```
=== "JavaScript"

    ```js
    if (token == VALUE_TOKEN) {
        callback(token);
    }
    ```
=== "TypeScript"

    ```ts
    if (token == VALUE_TOKEN) {
        callback(token);
    }
    ```

### Lonely if

A single if statement in an else block should be merged with the parent if
statement.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="3-4"
    if (validateCart(cart)) {
        processPayment(credentials);
    } else {
        if (cart.isEmpty()) {
            showEmptyCartError();
        } else {
            showError();
        }
    }
    ```
=== "Groovy"

    ```groovy hl_lines="3-4"
    if (validateCart(cart)) {
        processPayment(credentials)
    } else {
        if (cart.isEmpty()) {
            showEmptyCartError()
        } else {
            showError()
        }
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="3-4"
    if (validateCart(cart)) {
        processPayment(credentials)
    } else {
        if (cart.isEmpty()) {
            showEmptyCartError()
        } else {
            showError()
        }
    }
    ```
=== "C/C++"

    ```cpp hl_lines="3-4"
    if (validate_cart(cart)) {
        process_payment(credentials);
    } else {
        if (cart.is_empty()) {
            show_empty_cart_error();
        } else {
            show_error();
        }
    }
    ```
=== "Python"

    ```python hl_lines="3-4"
    if validate_cart(cart):
        process_payment(credentials)
    else:
        if not cart:
            show_empty_cart_error()
        else:
            show_error()
    ```
=== "JavaScript"

    ```js hl_lines="3-4"
    if (validateCart(cart)) {
        processPayment(credentials);
    } else {
        if (cart.isEmpty()) {
            showEmptyCartError();
        } else {
            showError();
        }
    }
    ```
=== "TypeScript"

    ```ts hl_lines="3-4"
    if (validateCart(cart)) {
        processPayment(credentials);
    } else {
        if (cart.isEmpty()) {
            showEmptyCartError();
        } else {
            showError();
        }
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="3"
    if (validateCart(cart)) {
        processPayment(credentials);
    } else if (cart.isEmpty()) {
        showEmptyCartError();
    } else {
        showError();
    }
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    if (validateCart(cart)) {
        processPayment(credentials)
    } else if (cart.isEmpty()) {
        showEmptyCartError()
    } else {
        showError()
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="3"
    if (validateCart(cart)) {
        processPayment(credentials)
    } else if (cart.isEmpty()) {
        showEmptyCartError()
    } else {
        showError()
    }
    ```
=== "C/C++"

    ```cpp hl_lines="3"
    if (validate_cart(cart)) {
        process_payment(credentials);
    } else if (cart.is_empty()) {
        show_empty_cart_error();
    } else {
        show_error();
    }
    ```
=== "Python"

    ```python hl_lines="3"
    if validate_cart(cart):
        process_payment(credentials)
    elif not cart:
        show_empty_cart_error()
    else:
        show_error()
    ```
=== "JavaScript"

    ```js hl_lines="3"
    if (validateCart(cart)) {
        processPayment(credentials);
    } else if (cart.isEmpty()) {
        showEmptyCartError();
    } else {
        showError();
    }
    ```
=== "TypeScript"

    ```ts hl_lines="3"
    if (validateCart(cart)) {
        processPayment(credentials);
    } else if (cart.isEmpty()) {
        showEmptyCartError();
    } else {
        showError();
    }
    ```

### Missing braces

Enforces the use of braces for multiline `if`, `else`, `for`, `while` and `do`
statements.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="1 3"
    if (validateCart(cart))
        processPayment(credentials);
    else
        showError();
    ```
=== "Groovy"

    ```groovy hl_lines="1 3"
    if (validateCart(cart))
        processPayment(credentials)
    else
        showError()
    ```
=== "Kotlin"

    ```kotlin hl_lines="1 3"
    if (validateCart(cart))
        processPayment(credentials)
    else
        showError()
    ```
=== "JavaScript"

    ```js hl_lines="1 3"
    if (validateCart(cart))
        processPayment(credentials);
    else
        showError();
    ```
=== "TypeScript"

    ```ts hl_lines="1 3"
    if (validateCart(cart))
        processPayment(credentials);
    else
        showError();
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="1 3 5"
    if (validateCart(cart)) {
        processPayment(credentials);
    } else {
        showError();
    }
    ```
=== "Groovy"

    ```groovy hl_lines="1 3 5"
    if (validateCart(cart)) {
        processPayment(credentials)
    } else {
        showError()
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="1 3 5"
    if (validateCart(cart)) {
        processPayment(credentials)
    } else {
        showError()
    }
    ```
=== "JavaScript"

    ```js hl_lines="1 3 5"
    if (validateCart(cart)) {
        processPayment(credentials);
    } else {
        showError();
    }
    ```
=== "TypeScript"

    ```ts hl_lines="1 3 5"
    if (validateCart(cart)) {
        processPayment(credentials);
    } else {
        showError();
    }
    ```

### Nested if-else

If a block ends with an if statement without else and the body is at least 2
lines, it should be inverted to avoid nesting and unnecessary indentation.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2 3"
    void login(User user) {
        if (user.isValid()) {
            if (!isLoggedIn(user)) {
                updateProfile(user);
                displayDashboard();
            }
        }
    }
    ```
=== "Groovy"

    ```groovy hl_lines="2 3"
    def login(User user) {
        if (user.isValid()) {
            if (!isLoggedIn(user)) {
                updateProfile(user)
                displayDashboard()
            }
        }
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="2 3"
    fun login(user: User) {
        if (user.isValid()) {
            if (!isLoggedIn(user)) {
                updateProfile(user)
                displayDashboard()
            }
        }
    }
    ```
=== "C/C++"

    ```cpp hl_lines="2 3"
    void login(User user) {
        if (user.is_valid()) {
            if (!is_logged_in(user)) {
                update_profile(user);
                display_dashboard();
            }
        }
    }
    ```
=== "Python"

    ```python hl_lines="2 3"
    def login(user):
        if user.is_valid():
            if not is_logged_in(user):
                update_profile(user)
                display_dashboard()
    ```
=== "JavaScript"

    ```js hl_lines="2 3"
    function login(user) {
        if (user.isValid()) {
            if (!isLoggedIn(user)) {
                updateProfile(user);
                displayDashboard();
            }
        }
    }
    ```
=== "TypeScript"

    ```ts hl_lines="2 3"
    function login(user: User): void {
        if (user.isValid()) {
            if (!isLoggedIn(user)) {
                updateProfile(user);
                displayDashboard();
            }
        }
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="2 5"
    void login(User user) {
        if (!user.isValid()) {
            return;
        }
        if (isLoggedIn(user)) {
            return;
        }
        updateProfile(user);
        displayDashboard();
    }
    ```
=== "Groovy"

    ```groovy hl_lines="2 5"
    def login(User user) {
        if (!user.isValid()) {
            return
        }
        if (isLoggedIn(user)) {
            return
        }
        updateProfile(user)
        displayDashboard()
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="2 5"
    fun login(user: User) {
        if (!user.isValid()) {
            return
        }
        if (isLoggedIn(user)) {
            return
        }
        updateProfile(user)
        displayDashboard()
    }
    ```
=== "C/C++"

    ```cpp hl_lines="2 5"
    void login(User user) {
        if (!user.is_valid()) {
            return;
        }
        if (is_logged_in(user)) {
            return;
        }
        update_profile(user);
        display_dashboard();
    }
    ```
=== "Python"

    ```python hl_lines="2 4"
    def login(user):
        if not user.is_valid():
            return
        if is_logged_in(user):
            return
        update_profile(user)
        display_dashboard()
    ```
=== "JavaScript"

    ```js hl_lines="2 5"
    function login(user) {
        if (!user.isValid()) {
            return;
        }
        if (isLoggedIn(user)) {
            return;
        }
        updateProfile(user);
        displayDashboard();
    }
    ```
=== "TypeScript"

    ```ts hl_lines="2 5"
    function login(user: User): void {
        if (!user.isValid()) {
            return;
        }
        if (isLoggedIn(user)) {
            return;
        }
        updateProfile(user);
        displayDashboard();
    }
    ```

### Redundant default

If every branch of a switch statement has a continue, return or throw statement,
the default branch can be lifted.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="5"
    void park(Car car) {
        switch (car) {
            case MOVING: throw new IllegalStateException();
            case PARKED: return;
            default: findParking(car);
        }
    }
    ```
=== "Groovy"

    ```groovy hl_lines="5"
    def park(Car car) {
        switch (car) {
            case MOVING: throw new IllegalStateException()
            case PARKED: return
            default: findParking(car)
        }
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="5"
    fun park(car: Car) {
        when {
            car.isMoving() -> throw IllegalStateException()
            car.isParked() -> return
            else -> findParking(car)
        }
    }
    ```
=== "C/C++"

    ```cpp hl_lines="5"
    void park(Car car) {
        switch (car) {
            case MOVING: throw IllegalStateException();
            case PARKED: return;
            default: findParking(car);
        }
    }
    ```
=== "Python"

    ```python hl_lines="5"
    def park(car):
        match car:
            case Car.MOVING: raise ValueError()
            case Car.PARKED: return
            case _: find_parking(car)
    ```
=== "JavaScript"

    ```js hl_lines="5"
    function park(car) {
        switch (car) {
            case MOVING: throw new Error();
            case PARKED: return;
            default: findParking(car);
        }
    }
    ```
=== "TypeScript"

    ```ts hl_lines="5"
    function park(car: Car): void {
        switch (car) {
            case MOVING: throw new Error();
            case PARKED: return;
            default: findParking(car);
        }
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="6"
    void park(Car car) {
        switch (car) {
            case MOVING: throw new IllegalStateException();
            case PARKED: return;
        }
        findParking(car);
    }
    ```
=== "Groovy"

    ```groovy hl_lines="6"
    def park(Car car) {
        switch (car) {
            case MOVING: throw new IllegalStateException()
            case PARKED: return
        }
        findParking(car)
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="6"
    fun park(car: Car) {
        when {
            car.isMoving() -> throw IllegalStateException()
            car.isParked() -> return
        }
        findParking(car)
    }
    ```
=== "C/C++"

    ```cpp hl_lines="6"
    void park(Car car) {
        switch (car) {
            case MOVING: throw IllegalStateException();
            case PARKED: return;
        }
        findParking(car);
    }
    ```
=== "Python"

    ```python hl_lines="5"
    def park(car):
        match car:
            case Car.MOVING: raise ValueError()
            case Car.PARKED: return
        find_parking(car)
    ```
=== "JavaScript"

    ```js hl_lines="6"
    function park(car) {
        switch (car) {
            case MOVING: throw new Error();
            case PARKED: return;
        }
        findParking(car);
    }
    ```
=== "TypeScript"

    ```ts hl_lines="6"
    function park(car: Car): void {
        switch (car) {
            case MOVING: throw new Error();
            case PARKED: return;
        }
        findParking(car);
    }
    ```

### Redundant else

When every if and else-if block has a continue, return or throw statement, the
else block can be lifted.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="4 6"
    void park(Car car) {
        if (car.isMoving()) {
            throw new IllegalStateException();
        } else if (car.isParked()) {
            return;
        } else {
            findParking(car);
        }
    }
    ```
=== "Groovy"

    ```groovy hl_lines="4 6"
    def park(Car car) {
        if (car.isMoving()) {
            throw new IllegalStateException()
        } else if (car.isParked()) {
            return
        } else {
            findParking(car)
        }
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="4 6"
    fun park(car: Car) {
        if (car.isMoving()) {
            throw IllegalStateException()
        } else if (car.isParked()) {
            return
        } else {
            findParking(car)
        }
    }
    ```
=== "C/C++"

    ```cpp hl_lines="4 6"
    void park(Car car) {
        if (car.is_moving()) {
            throw IllegalStateException();
        } else if (car.is_parked()) {
            return;
        } else {
            findParking(car);
        }
    }
    ```
=== "Python"

    ```python hl_lines="4 6"
    def park(car):
        if car.is_moving():
            raise ValueError()
        elif car.is_parked():
            return
        else:
            find_parking(car)
    ```
=== "JavaScript"

    ```js hl_lines="4 6"
    function park(car) {
        if (car.isMoving()) {
            throw new Error();
        } else if (car.isParked()) {
            return;
        } else {
            findParking(car);
        }
    }
    ```
=== "TypeScript"

    ```ts hl_lines="4 6"
    function park(car: Car): void {
        if (car.isMoving()) {
            throw new Error();
        } else if (car.isParked()) {
            return;
        } else {
            findParking(car);
        }
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="5 8"
    void park(Car car) {
        if (car.isMoving()) {
            throw new IllegalStateException();
        }
        if (car.isParked()) {
            return;
        }
        findParking(car);
    }
    ```
=== "Groovy"

    ```groovy hl_lines="5 8"
    def park(Car car) {
        if (car.isMoving()) {
            throw new IllegalStateException()
        }
        if (car.isParked()) {
            return
        }
        findParking(car)
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="5 8"
    fun park(car: Car) {
        if (car.isMoving()) {
            throw IllegalStateException()
        }
        if (car.isParked()) {
            return
        }
        findParking(car)
    }
    ```
=== "C/C++"

    ```cpp hl_lines="5 8"
    void park(Car car) {
        if (car.is_moving()) {
            throw IllegalStateException();
        }
        if (car.is_parked()) {
            return;
        }
        findParking(car);
    }
    ```
=== "Python"

    ```python hl_lines="4 6"
    def park(car):
        if car.is_moving():
            raise ValueError()
        if car.is_parked():
            return
        find_parking(car)
    ```
=== "JavaScript"

    ```js hl_lines="5 8"
    function park(car) {
        if (car.isMoving()) {
            throw new Error();
        }
        if (car.isParked()) {
            return;
        }
        findParking(car);
    }
    ```
=== "TypeScript"

    ```ts hl_lines="5 8"
    function park(car: Car): void {
        if (car.isMoving()) {
            throw new Error();
        }
        if (car.isParked()) {
            return;
        }
        findParking(car);
    }
    ```

### Redundant if

If-else statement that returns boolean can be simplified by returning the
condition.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2 4"
    boolean isValid(User user) {
        if (user.isActive()) {
            return true;
        } else {
            return false;
        }
    }
    ```
=== "Groovy"

    ```groovy hl_lines="2 4"
    boolean isValid(User user) {
        if (user.isActive()) {
            return true
        } else {
            return false
        }
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="2 4"
    fun isValid(user: User): Boolean {
        if (user.isActive()) {
            return true
        } else {
            return false
        }
    }
    ```
=== "C/C++"

    ```cpp hl_lines="2 4"
    bool is_valid(User user) {
        if (user.is_active()) {
            return true;
        } else {
            return false;
        }
    }
    ```
=== "Python"

    ```python hl_lines="2 4"
    def is_valid(user):
        if user.is_active():
            return True
        else:
            return False
    ```
=== "JavaScript"

    ```js hl_lines="2 4"
    function isValid(user) {
        if (user.isActive()) {
            return true;
        } else {
            return false;
        }
    }
    ```
=== "TypeScript"

    ```ts hl_lines="2 4"
    function isValid(user: User): boolean {
        if (user.isActive()) {
            return true;
        } else {
            return false;
        }
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="2"
    boolean isValid(User user) {
        return user.isActive();
    }
    ```
=== "Groovy"

    ```groovy hl_lines="2"
    boolean isValid(User user) {
        return user.isActive()
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="2"
    fun isValid(user: User): Boolean {
        return user.isActive()
    }
    ```
=== "C/C++"

    ```cpp hl_lines="2"
    bool is_valid(User user) {
        return user.is_active();
    }
    ```
=== "Python"

    ```python hl_lines="2"
    def is_valid(user):
        return user.is_active()
    ```
=== "JavaScript"

    ```js hl_lines="2"
    function isValid(user) {
        return user.isActive();
    }
    ```
=== "TypeScript"

    ```ts hl_lines="2"
    function isValid(user: User): boolean {
        return user.isActive();
    }
    ```

### Semicolon

Do not use semicolons at the end of statements in languages that do not require
them.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"

    ```groovy
    sendEmail(user);
    ```
=== "Kotlin"

    ```kotlin
    sendEmail(user);
    ```
=== "Python"

    ```python
    send_email(user);
    ```
=== "JavaScript"

    ```js
    sendEmail(user)
    ```
=== "TypeScript"

    ```ts
    sendEmail(user)
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"

    ```groovy
    sendEmail(user)
    ```
=== "Kotlin"

    ```kotlin
    sendEmail(user)
    ```
=== "Python"

    ```python
    send_email(user)
    ```
=== "JavaScript"

    ```js
    sendEmail(user);
    ```
=== "TypeScript"

    ```ts
    sendEmail(user);
    ```

!!! warning

    Semicolons are enforced in JavaScript and TypeScript to prevent issues with
    automatic semicolon insertion.

### Unnecessary continue

The last continue statement in a loop is useless.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="5"
    for (User user : users) {
        if (user.isActive()) {
            sendEmail(user);
        }
        continue;
    }
    ```
=== "Groovy"

    ```groovy hl_lines="5"
    for (User user : users) {
        if (user.isActive()) {
            sendEmail(user)
        }
        continue
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="5"
    for (user in users) {
        if (user.isActive()) {
            sendEmail(user)
        }
        continue
    }
    ```
=== "C/C++"

    ```cpp hl_lines="5"
    for (User user : users) {
        if (user.is_active()) {
            send_email(user);
        }
        continue;
    }
    ```
=== "Python"

    ```python hl_lines="4"
    for user in users:
        if user.is_active():
            send_email(user)
        continue
    ```
=== "JavaScript"

    ```js hl_lines="5"
    for (const user of users) {
        if (user.isActive()) {
            sendEmail(user);
        }
        continue;
    }
    ```
=== "TypeScript"

    ```ts hl_lines="5"
    for (const user of users) {
        if (user.isActive()) {
            sendEmail(user);
        }
        continue;
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    for (User user : users) {
        if (user.isActive()) {
            sendEmail(user);
        }
    }
    ```
=== "Groovy"

    ```groovy
    for (User user : users) {
        if (user.isActive()) {
            sendEmail(user)
        }
    }
    ```
=== "Kotlin"

    ```kotlin
    for (user in users) {
        if (user.isActive()) {
            sendEmail(user)
        }
    }
    ```
=== "C/C++"

    ```cpp
    for (User user : users) {
        if (user.is_active()) {
            send_email(user);
        }
    }
    ```
=== "Python"

    ```python
    for user in users:
        if user.is_active():
            send_email(user)
    ```
=== "JavaScript"

    ```js
    for (const user of users) {
        if (user.isActive()) {
            sendEmail(user);
        }
    }
    ```
=== "TypeScript"

    ```ts
    for (const user of users) {
        if (user.isActive()) {
            sendEmail(user);
        }
    }
    ```

### Unnecessary return

The last return statement in a function is useless.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="7"
    void sendEmails(List<User> users) {
        for (User user : users) {
            if (user.isActive()) {
                sendEmail(user);
            }
        }
        return;
    }
    ```
=== "Groovy"

    ```groovy hl_lines="7"
    void sendEmails(List<User> users) {
        for (User user : users) {
            if (user.isActive()) {
                sendEmail(user)
            }
        }
        return
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="7"
    fun sendEmails(users: List<User>) {
        for (user in users) {
            if (user.isActive()) {
                sendEmail(user)
            }
        }
        return
    }
    ```
=== "C/C++"

    ```cpp hl_lines="7"
    void send_emails(std::vector<User> users) {
        for (User user : users) {
            if (user.is_active()) {
                send_email(user);
            }
        }
        return;
    }
    ```
=== "Python"

    ```python hl_lines="5"
    def send_emails(users):
        for user in users:
            if user.is_active():
                send_email(user)
        return
    ```
=== "JavaScript"

    ```js hl_lines="7"
    function sendEmails(users) {
        for (const user of users) {
            if (user.isActive()) {
                sendEmail(user);
            }
        }
        return;
    }
    ```
=== "TypeScript"

    ```ts hl_lines="7"
    function sendEmails(users: User[]): void {
        for (const user of users) {
            if (user.isActive()) {
                sendEmail(user);
            }
        }
        return;
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    void sendEmails(List<User> users) {
        for (User user : users) {
            if (user.isActive()) {
                sendEmail(user);
            }
        }
    }
    ```
=== "Groovy"

    ```groovy
    void sendEmails(List<User> users) {
        for (User user : users) {
            if (user.isActive()) {
                sendEmail(user)
            }
        }
    }
    ```
=== "Kotlin"

    ```kotlin
    fun sendEmails(users: List<User>) {
        for (user in users) {
            if (user.isActive()) {
                sendEmail(user)
            }
        }
    }
    ```
=== "C/C++"

    ```cpp
    void send_emails(std::vector<User> users) {
        for (User user : users) {
            if (user.is_active()) {
                send_email(user);
            }
        }
    }
    ```
=== "Python"

    ```python
    def send_emails(users):
        for user in users:
            if user.is_active():
                send_email(user)
    ```
=== "JavaScript"

    ```js
    function sendEmails(users) {
        for (const user of users) {
            if (user.isActive()) {
                sendEmail(user);
            }
        }
    }
    ```
=== "TypeScript"

    ```ts
    function sendEmails(users: User[]): void {
        for (const user of users) {
            if (user.isActive()) {
                sendEmail(user);
            }
        }
    }
    ```

## Testing

### Complicated assertion

Use targeted assertion methods instead of general ones with complicated
conditions.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    assertTrue(username == null);
    ```
=== "Groovy"

    ```groovy
    assertTrue(username == null)
    ```
=== "Kotlin"

    ```kotlin
    assertTrue(username == null)
    ```
=== "Python"

    ```python
    self.assertTrue(username is None)
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    assertNull(username);
    ```
=== "Groovy"

    ```groovy
    assertNull(username)
    ```
=== "Kotlin"

    ```kotlin
    assertNull(username)
    ```
=== "Python"

    ```python
    self.assertIsNone(username)
    ```

### Confusing assertion

Flip assertions instead of negating conditions.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    assertFalse(!user.isActive());
    ```
=== "Groovy"

    ```groovy
    assertFalse(!user.isActive())
    ```
=== "Kotlin"

    ```kotlin
    assertFalse(!user.isActive())
    ```
=== "Python"

    ```python
    self.assertFalse(!user.is_active())
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    assertTrue(user.isActive());
    ```
=== "Groovy"

    ```groovy
    assertTrue(user.isActive())
    ```
=== "Kotlin"

    ```kotlin
    assertTrue(user.isActive())
    ```
=== "Python"

    ```python
    self.assertTrue(user.is_active())
    ```

### Deprecated annotation

Prefer Kotlin test annotations over JUnit ones.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"

    ```kotlin hl_lines="1"
    import org.junit.Test

    class UserTest {
        @Test
        fun testUser() {}
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"

    ```kotlin hl_lines="1"
    import kotlin.test.Test

    class UserTest {
        @Test
        fun testUser() {}
    }
    ```

## Trimming

### Block comment trim

Do not start or end block comments with whitespaces.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2 5"
    /**
     *
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     *
     */
    ```
=== "Groovy"

    ```groovy hl_lines="2 5"
    /**
     *
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     *
     */
    ```
=== "Kotlin"

    ```kotlin hl_lines="2 5"
    /**
     *
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     *
     */
    ```
=== "C/C++"

    ```cpp hl_lines="2 5"
    /**
     *
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     *
     */
    ```
=== "Python"

    ```python hl_lines="2 5"
    """

    AUTHOR: John Doe
    LICENSE: Apache 2.0

    """
    ```
=== "JavaScript"

    ```js hl_lines="2 5"
    /**
     *
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     *
     */
    ```
=== "TypeScript"

    ```ts hl_lines="2 5"
    /**
     *
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     *
     */
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    /**
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     */
    ```
=== "Groovy"

    ```groovy
    /**
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     */
    ```
=== "Kotlin"

    ```kotlin
    /**
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     */
    ```
=== "C/C++"

    ```cpp
    /**
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     */
    ```
=== "Python"

    ```python
    """
    AUTHOR: John Doe
    LICENSE: Apache 2.0
    """
    ```
=== "JavaScript"

    ```js
    /**
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     */
    ```
=== "TypeScript"

    ```ts
    /**
     * AUTHOR: John Doe
     * LICENSE: Apache 2.0
     */
    ```

### Braces trim

Prohibits empty first and last lines in code blocks.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2 5"
    void onReceive(Integer value) {

        if (value != null) {
            total += value;

        }
    }
    ```
=== "Groovy"

    ```groovy hl_lines="2 5"
    def onReceive(Integer value) {

        if (value != null) {
            total += value

        }
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="2 5"
    fun onReceive(value: Int?) {

        if (value != null) {
            total += value

        }
    }
    ```
=== "C/C++"

    ```cpp hl_lines="2 5"
    void onReceive(int value) {

        if (value != 0) {
            total += value;

        }
    }
    ```
=== "Python"

    ```python hl_lines="2 6"
    foo = {

        'bar',
        'baz',
        'qux',

    }
    ```
=== "JavaScript"

    ```js hl_lines="2 5"
    function onReceive(value) {

        if (value != null) {
            total += value;

        }
    }
    ```
=== "TypeScript"

    ```ts hl_lines="2 5"
    function onReceive(value: number | null): void {

        if (value != null) {
            total += value;

        }
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    void onReceive(Integer value) {
        if (value != null) {
            total += value;
        }
    }
    ```
=== "Groovy"

    ```groovy
    def onReceive(Integer value) {
        if (value != null) {
            total += value
        }
    }
    ```
=== "Kotlin"

    ```kotlin
    fun onReceive(value: Int?) {
        if (value != null) {
            total += value
        }
    }
    ```
=== "C/C++"

    ```cpp
    void onReceive(int value) {
        if (value != 0) {
            total += value;
        }
    }
    ```
=== "Python"

    ```python
    foo = {
        'bar',
        'baz',
        'qux',
    }
    ```
=== "JavaScript"

    ```js
    function onReceive(value) {
        if (value != null) {
            total += value;
        }
    }
    ```
=== "TypeScript"

    ```ts
    function onReceive(value: number | null): void {
        if (value != null) {
            total += value;
        }
    }
    ```

### Brackets trim

Prohibits empty first and last lines in collection initializers.

**:material-star-four-points-outline:{ #accent } Before**

=== "Groovy"

    ```groovy hl_lines="2 6"
    var pond = [

        new Fish('Nemo'),
        new Fish('Dory'),
        new Fish('Marlin'),

    ]
    ```
=== "C/C++"

    ```cpp hl_lines="3 5"
    Fish dory =
        pond[

            0

        ];
    ```
=== "Python"

    ```python hl_lines="2 6"
    pond = [

        Fish('Nemo'),
        Fish('Dory'),
        Fish('Marlin'),

    ]
    ```
=== "JavaScript"

    ```js hl_lines="2 6"
    const pond = [

        new Fish('Nemo'),
        new Fish('Dory'),
        new Fish('Marlin'),

    ]
    ```
=== "TypeScript"

    ```ts hl_lines="2 6"
    const pond: Fish[] = [

        new Fish('Nemo'),
        new Fish('Dory'),
        new Fish('Marlin'),

    ]
    ```

**:material-star-four-points:{ #accent } After**

=== "Groovy"

    ```groovy
    var pond = [
        new Fish('Nemo'),
        new Fish('Dory'),
        new Fish('Marlin'),
    ]
    ```
=== "C/C++"

    ```cpp
    Fish dory =
        pond[
            0
        ];
    ```
=== "Python"

    ```python
    pond = [
        Fish('Nemo'),
        Fish('Dory'),
        Fish('Marlin'),
    ]
    ```
=== "JavaScript"

    ```js
    const pond = [
        new Fish('Nemo'),
        new Fish('Dory'),
        new Fish('Marlin'),
    ]
    ```
=== "TypeScript"

    ```ts
    const pond: Fish[] = [
        new Fish('Nemo'),
        new Fish('Dory'),
        new Fish('Marlin'),
    ]
    ```

### Comment trim

Prohibits empty first and last lines in EOL comments.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="1 4"
    //
    // This is a
    // multiline comment
    //
    ```
=== "Groovy"

    ```groovy hl_lines="1 4"
    //
    // This is a
    // multiline comment
    //
    ```
=== "Kotlin"

    ```kotlin hl_lines="1 4"
    //
    // This is a
    // multiline comment
    //
    ```
=== "C/C++"

    ```cpp hl_lines="1 4"
    //
    // This is a
    // multiline comment
    //
    ```
=== "Python"

    ```python hl_lines="1 4"
    #
    # This is a
    # multiline comment
    #
    ```
=== "JavaScript"

    ```js hl_lines="1 4"
    //
    // This is a
    // multiline comment
    //
    ```
=== "TypeScript"

    ```ts hl_lines="1 4"
    //
    // This is a
    // multiline comment
    //
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    // This is a
    // multiline comment
    ```
=== "Groovy"

    ```groovy
    // This is a
    // multiline comment
    ```
=== "Kotlin"

    ```kotlin
    // This is a
    // multiline comment
    ```
=== "C/C++"

    ```cpp
    // This is a
    // multiline comment
    ```
=== "Python"

    ```python
    # This is a
    # multiline comment
    ```
=== "JavaScript"

    ```js
    // This is a
    // multiline comment
    ```
=== "TypeScript"

    ```ts
    // This is a
    // multiline comment
    ```

### Duplicate blank line

Prohibits consecutive blank lines in the code.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="3"
    String message = "Hello";


    System.out.println(message);
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    var message = 'Hello'


    println(message)
    ```
=== "Kotlin"

    ```kotlin hl_lines="3"
    val message = "Hello"


    println(message)
    ```
=== "C/C++"

    ```cpp hl_lines="3"
    std::string message = "Hello";


    std::cout << message << std::endl;
    ```
=== "Python"

    ```python hl_lines="3"
    message = 'Hello'


    print(message)
    ```
=== "JavaScript"

    ```js hl_lines="3"
    const message = 'Hello';


    console.log(message);
    ```
=== "TypeScript"

    ```ts hl_lines="3"
    const message: string = 'Hello';


    console.log(message);
    ```

!!! warning

    PEP8 allows two blank lines between top-level functions and class
    definitions.

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    String message = "Hello";

    System.out.println(message);
    ```
=== "Groovy"

    ```groovy
    var message = 'Hello'

    println(message)
    ```
=== "Kotlin"

    ```kotlin
    val message = "Hello"

    println(message)
    ```
=== "C/C++"

    ```cpp
    std::string message = "Hello";

    std::cout << message << std::endl;
    ```
=== "Python"

    ```python
    message = 'Hello'

    print(message)
    ```
=== "JavaScript"

    ```js
    const message = 'Hello';

    console.log(message);
    ```
=== "TypeScript"

    ```ts
    const message: string = 'Hello';

    console.log(message);
    ```

### Duplicate blank line in block comment

Prohibits consecutive blank lines in block comments.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="4"
    /**
     * This is a
     *
     *
     * very long comment
     */
    ```
=== "Groovy"

    ```groovy hl_lines="4"
    /**
     * This is a
     *
     *
     * very long comment
     */
    ```
=== "Kotlin"

    ```kotlin hl_lines="4"
    /**
     * This is a
     *
     *
     * very long comment
     */
    ```
=== "C/C++"

    ```cpp hl_lines="4"
    /**
     * This is a
     *
     *
     * very long comment
     */
    ```
=== "Python"

    ```python hl_lines="4"
    """
    This is a


    very long comment
    """
    ```
=== "JavaScript"

    ```js hl_lines="4"
    /**
     * This is a
     *
     *
     * very long comment
     */
    ```
=== "TypeScript"

    ```ts hl_lines="4"
    /**
     * This is a
     *
     *
     * very long comment
     */
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    /**
     * This is a
     *
     * very long comment
     */
    ```
=== "Groovy"

    ```groovy
    /**
     * This is a
     *
     * very long comment
     */
    ```
=== "Kotlin"

    ```kotlin
    /**
     * This is a
     *
     * very long comment
     */
    ```
=== "C/C++"

    ```cpp
    /**
     * This is a
     *
     * very long comment
     */
    ```
=== "Python"

    ```python
    """
    This is a

    very long comment
    """
    ```
=== "JavaScript"

    ```js
    /**
     * This is a
     *
     * very long comment
     */
    ```
=== "TypeScript"

    ```ts
    /**
     * This is a
     *
     * very long comment
     */
    ```

### Duplicate blank line in comment

Prohibits consecutive blank lines in comments.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="3"
    // This is a
    //
    //
    // very long comment
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    // This is a
    //
    //
    // very long comment
    ```
=== "Kotlin"

    ```kotlin hl_lines="3"
    // This is a
    //
    //
    // very long comment
    ```
=== "C/C++"

    ```cpp hl_lines="3"
    // This is a
    //
    //
    // very long comment
    ```
=== "Python"

    ```python hl_lines="3"
    # This is a
    #
    #
    # very long comment
    ```
=== "JavaScript"

    ```js hl_lines="3"
    // This is a
    //
    //
    // very long comment
    ```
=== "TypeScript"

    ```ts hl_lines="3"
    // This is a
    //
    //
    // very long comment
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    // This is a
    //
    // very long comment
    ```
=== "Groovy"

    ```groovy
    // This is a
    //
    // very long comment
    ```
=== "Kotlin"

    ```kotlin
    // This is a
    //
    // very long comment
    ```
=== "C/C++"

    ```cpp
    // This is a
    //
    // very long comment
    ```
=== "Python"

    ```python
    # This is a
    #
    # very long comment
    ```
=== "JavaScript"

    ```js
    // This is a
    //
    // very long comment
    ```
=== "TypeScript"

    ```ts
    // This is a
    //
    // very long comment
    ```

### Duplicate space

Prohibits consecutive spaces in the code.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    double tax      = 0.2;
    double subtotal = bill     * tax;
    double total    = subtotal + bill;
    ```
=== "Groovy"

    ```groovy
    var tax      = 0.2d
    var subtotal = bill     * tax
    var total    = subtotal + bill
    ```
=== "Kotlin"

    ```kotlin
    val tax      = 0.2d
    val subtotal = bill     * tax
    val total    = subtotal + bill
    ```
=== "C/C++"

    ```cpp
    double tax      = 0.2;
    double subtotal = bill     * tax;
    double total    = subtotal + bill;
    ```
=== "Python"

    ```python
    tax      = 0.2
    subtotal = bill     * tax
    total    = subtotal + bill
    ```
=== "JavaScript"

    ```js
    const tax      = 0.2;
    const subtotal = bill     * tax;
    const total    = subtotal + bill;
    ```
=== "TypeScript"

    ```ts
    const tax: number      = 0.2;
    const subtotal: number = bill     * tax;
    const total: number    = subtotal + bill;
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    double tax = 0.2;
    double subtotal = bill * tax;
    double total = subtotal + bill;
    ```
=== "Groovy"

    ```groovy
    var tax = 0.2d
    var subtotal = bill * tax
    var total = subtotal + bill
    ```
=== "Kotlin"

    ```kotlin
    val tax = 0.2d
    val subtotal = bill * tax
    val total = subtotal + bill
    ```
=== "C/C++"

    ```cpp
    double tax = 0.2;
    double subtotal = bill * tax;
    double total = subtotal + bill;
    ```
=== "Python"

    ```python
    tax = 0.2
    subtotal = bill * tax
    total = subtotal + bill
    ```
=== "JavaScript"

    ```js
    const tax = 0.2;
    const subtotal = bill * tax;
    const total = subtotal + bill;
    ```
=== "TypeScript"

    ```ts
    const tax: number = 0.2;
    const subtotal: number = bill * tax;
    const total: number = subtotal + bill;
    ```

### Parentheses trim

Prohibits empty first and last lines in method declarations and calls.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2 8"
    void swim(

        Fish fish,
        Pond pond
    ) {
        pond.release(
            fish

        );
    }
    ```
=== "Groovy"

    ```groovy hl_lines="2 8"
    def swim(

        Fish fish,
        Pond pond
    ) {
        pond.release(
            fish,

        )
    }
    ```
=== "Kotlin"

    ```kotlin hl_lines="2 8"
    fun swim(

        Fish fish,
        Pond pond,
    ) {
        pond.release(
            fish,

        )
    }
    ```
=== "C/C++"

    ```cpp hl_lines="2 8"
    void swim(

        Fish fish,
        Pond pond
    ) {
        pond.release(
            fish

        );
    }
    ```
=== "Python"

    ```python hl_lines="3 12 15 17"
    def swim(

        fish,
        pond,
    ):
        pond.release(
            fish,

        )
    ```
=== "JavaScript"

    ```js hl_lines="2 8"
    function swim(

        fish,
        pond,
    ) {
        pond.release(
            fish,

        );
    }
    ```
=== "TypeScript"

    ```ts hl_lines="2 8"
    function swim(

        fish: Fish,
        pond: Pond,
    ): void {
        pond.release(
            fish,

        );
    }
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    void swim(
        Fish fish,
        Pond pond
    ) {
        pond.release(
            fish
        );
    }
    ```
=== "Groovy"

    ```groovy
    def swim(
        Fish fish,
        Pond pond
    ) {
        pond.release(
            fish,
        )
    }
    ```
=== "Kotlin"

    ```kotlin
    fun swim(
        fish: Fish,
        pond: Pond,
    ) {
        pond.release(
            fish,
        )
    }
    ```
=== "C/C++"

    ```cpp
    void swim(
        Fish fish,
        Pond pond
    ) {
        pond.release(
            fish
        );
    }
    ```
=== "Python"

    ```python
    def swim(
        fish,
        pond,
    ):
        pond.release(
            fish,
        )
    ```
=== "JavaScript"

    ```js
    function swim(
        fish,
        pond,
    ) {
        pond.release(
            fish,
        );
    }
    ```
=== "TypeScript"

    ```ts
    function swim(
        fish: Fish,
        pond: Pond,
    ): void {
        pond.release(
            fish,
        );
    }
    ```

### Tags trim

Prohibits empty first and last lines in generic type parameters.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2 6"
    Triple<

        String,
        Integer,
        Boolean

    > userAgeMarried = new Triple<>("Hank Hill", 41, true);
    ```
=== "Groovy"

    ```groovy hl_lines="2 6"
    Triple<

        String,
        Integer,
        Boolean

    > userAgeMarried = new Triple<>('Hank Hill', 41, true)
    ```
=== "Kotlin"

    ```kotlin hl_lines="3 7"
    val userAgeMarried =
        new Triple<

            String,
            Integer,
            Boolean

        >('Hank Hill', 41, true)
    ```
=== "C/C++"

    ```cpp hl_lines="2 6"
    template<

        typename T1,
        typename T2,
        typename T3

    >
    struct Triple {
        T1 first;
        T2 second;
        T3 third;
    };
    ```
=== "TypeScript"

    ```ts hl_lines="2 6"
    type Triple<

      T1,
      T2,
      T3,

    > = {
        first: T1;
        second: T2;
        third: T3;
    };
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    Triple<
        String,
        Integer,
        Boolean
    > userAgeMarried = new Triple<>("Hank Hill", 41, true);
    ```
=== "Groovy"

    ```groovy
    Triple<
        String,
        Integer,
        Boolean
    > userAgeMarried = new Triple<>('Hank Hill', 41, true)
    ```
=== "Kotlin"

    ```kotlin
    val userAgeMarried =
        Triple<

            String,
            Integer,
            Boolean

        >('Hank Hill', 41, true)
    ```
=== "C/C++"

    ```cpp
    template<
        typename T1,
        typename T2,
        typename T3
    >
    struct Triple {
        T1 first;
        T2 second;
        T3 third;
    };
    ```
=== "TypeScript"

    ```ts
    type Triple<
      T1,
      T2,
      T3,
    > = {
        first: T1;
        second: T2;
        third: T3;
    };
    ```

### Unnecessary blank line after colon

Prohibits first empty line in Python function and class definitions.

**:material-star-four-points-outline:{ #accent } Before**

=== "Python"

    ```python hl_lines="2"
    def on_receive(value):

        if value is not None:
            total += value
    ```

**:material-star-four-points:{ #accent } After**

=== "Python"

    ```python
    def on_receive(value):
        if value is not None:
            total += value
    ```

### Unnecessary initial blank line

The first line of a file cannot be a blank line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="1"
    \n
    package com.example;

    void execute() {}
    ```
=== "Groovy"

    ```groovy hl_lines="1"
    \n
    package com.example

    def execute() {}
    ```
=== "Kotlin"

    ```kotlin hl_lines="1"
    \n
    package com.example

    fun execute() {}
    ```
=== "Python"

    ```python hl_lines="1"
    \n
    def execute():
        pass
    ```
=== "JavaScript"

    ```js hl_lines="1"
    \n
    function execute() {}
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    \n
    function execute(): void {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    package com.example;

    void execute() {}
    ```
=== "Groovy"

    ```groovy
    package com.example

    def execute() {}
    ```
=== "Kotlin"

    ```kotlin
    package com.example

    fun execute() {}
    ```
=== "Python"

    ```python
    def execute():
        pass
    ```
=== "JavaScript"

    ```js
    function execute() {}
    ```
=== "TypeScript"

    ```ts
    function execute(): void {}
    ```

## Wrapping

### Assignment wrap

Assignee and the value of assignment spanning multiple lines should be separated
by a newline.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="1"
    String message = new StringBuilder()
        .append("Hello")
        .append(" World")
        .toString();
    ```
=== "Groovy"

    ```groovy hl_lines="1"
    var message = new StringBuilder()
        .append('Hello')
        .append(' World')
        .toString()
    ```
=== "Kotlin"

    ```kotlin hl_lines="1"
    val message = buildString {
        append("Hello")
        append(" World")
    }
    ```
=== "C/C++"

    ```cpp hl_lines="1"
    std::string message = std::string()
        .append("Hello")
        .append(" World")
        .toString();
    ```
=== "JavaScript"

    ```js hl_lines="1"
    const message = 'Hello'
        .concat(' World')
    ```
=== "TypeScript"

    ```ts hl_lines="1"
    const message: string = 'Hello'
        .concat(' World')
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="2"
    String message =
        new StringBuilder()
            .append("Hello")
            .append(" World")
            .toString();
    ```
=== "Groovy"

    ```groovy hl_lines="2"
    var message =
        new StringBuilder()
            .append('Hello')
            .append(' World')
            .toString()
    ```
=== "Kotlin"

    ```kotlin hl_lines="2"
    val message =
        buildString {
            append("Hello")
            append(" World")
        }
    ```
=== "C/C++"

    ```cpp hl_lines="2"
    std::string message =
        std::string()
            .append("Hello")
            .append(" World")
            .toString();
    ```
=== "JavaScript"

    ```js hl_lines="2"
    const message: string =
        'Hello'
            .concat(' World')
    ```
=== "TypeScript"

    ```ts hl_lines="2"
    const message: string =
        'Hello'
            .concat(' World')
    ```

### Chain call wrap

Each method call in a chain should be aligned with the dot operator.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2"
    int senderId =
        notification.getSender()
            .getId();
    ```
=== "Groovy"

    ```groovy hl_lines="2"
    var senderId =
        notification.getSender()
            .getId()
    ```
=== "Kotlin"

    ```kotlin hl_lines="2"
    val senderId =
        notification.getSender()
            .id.takeIf { it.isNotBlank() }
    ```
=== "C/C++"

    ```cpp hl_lines="2"
    int senderId =
        notification.getSender()
            ->getId();
    ```
=== "JavaScript"

    ```js hl_lines="2"
    const senderId =
        notification.getSender()
            .getId();
    ```
=== "TypeScript"

    ```ts hl_lines="2"
    const senderId: number =
        notification.getSender()
            .getId();
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="3"
    int senderId =
        notification
            .getSender()
            .getId();
    ```
=== "Groovy"

    ```groovy hl_lines="3"
    var senderId =
        notification
            .getSender()
            .getId()
    ```
=== "Kotlin"

    ```kotlin hl_lines="3"
    val senderId =
        notification
            .getSender()
            .id
    ```
=== "C/C++"

    ```cpp hl_lines="3"
    int senderId =
        notification
            ->getSender()
            ->getId();
    ```
=== "JavaScript"

    ```js hl_lines="3"
    const senderId =
        notification
            .getSender()
            .getId();
    ```
=== "TypeScript"

    ```ts hl_lines="3"
    const senderId: number =
        notification
            .getSender()
            .getId();
    ```

### Elvis wrap

In a multiline statement, the elvis operator should be separated into a new line
instead of trailing the statement.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"

    ```kotlin hl_lines="2"
    user.name
        .takeIf { it.isNotBlank() } ?: "Unknown"
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"

    ```kotlin hl_lines="3"
    user.name
        .takeIf { it.isNotBlank() }
        ?: "Unknown"
    ```

### Infix call wrap

When breaking an infix function call, the operator should be placed at the end
of the line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Kotlin"

    ```kotlin hl_lines="4 6"
    val ages =
        mapOf(
            "Alice"
                to 25,
            "Bob"
                to 30,
        )
    ```

**:material-star-four-points:{ #accent } After**

=== "Kotlin"

    ```kotlin hl_lines="3 5"
    val ages =
        mapOf(
            "Alice" to
                25,
            "Bob" to
                30,
        )
    ```

### Lambda wrap

When breaking a multiline lambda expression, the body should be placed on a new
line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="4"
    int sum =
        IntStream
            .range(0, 10)
            .map(i -> i
                * 2
            ).sum();
    ```
=== "Groovy"

    ```groovy hl_lines="4"
    var sum =
        IntStream
            .range(0, 10)
            .map { i -> i
                * 2
            }.sum()
    ```
=== "JavaScript"

    ```js hl_lines="4"
    const sum =
        Array
            .from({ length: 10 }, (_, i) => i)
            .map(i => i
                * 2
            ).reduce((a, b) => a + b, 0);
    ```
=== "TypeScript"

    ```ts hl_lines="4"
    const sum: number =
        Array
            .from({ length: 10 }, (_, i) => i)
            .map(i => i
                * 2
            ).reduce((a, b) => a + b, 0);
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="5"
    int sum =
        IntStream
            .range(0, 10)
            .map(i ->
                i * 2
            ).sum();
    ```
=== "Groovy"

    ```groovy hl_lines="5"
    var sum =
        IntStream
            .range(0, 10)
            .map { i ->
                i * 2
            }.sum()
    ```
=== "JavaScript"

    ```js hl_lines="5"
    const sum =
        Array
            .from({ length: 10 }, (_, i) => i)
            .map(i =>
                i * 2
            ).reduce((a, b) => a + b, 0);
    ```
=== "TypeScript"

    ```ts hl_lines="5"
    const sum: number =
        Array
            .from({ length: 10 }, (_, i) => i)
            .map(i =>
                i * 2
            ).reduce((a, b) => a + b, 0);
    ```

### Operator wrap

A line break should be placed after the operator in a binary expression.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="3-4"
    int total =
        subtotal
        + tax
        - discount;
    ```
=== "Groovy"

    ```groovy hl_lines="3-4"
    var total =
        subtotal
        + tax
        - discount
    ```
=== "Kotlin"

    ```kotlin hl_lines="3-4"
    val total =
        subtotal
        + tax
        - discount
    ```
=== "C/C++"

    ```cpp hl_lines="3-4"
    int total =
        subtotal
        + tax
        - discount;
    ```
=== "JavaScript"

    ```js hl_lines="3-4"
    const total =
        subtotal
        + tax
        - discount;
    ```
=== "TypeScript"

    ```ts hl_lines="3-4"
    const total: number =
        subtotal
        + tax
        - discount;
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="2-3"
    int total =
        subtotal +
        tax -
        discount;
    ```
=== "Groovy"

    ```groovy hl_lines="2-3"
    var total =
        subtotal +
        tax -
        discount
    ```
=== "Kotlin"

    ```kotlin hl_lines="2-3"
    val total =
        subtotal +
        tax -
        discount
    ```
=== "C/C++"

    ```cpp hl_lines="2-3"
    int total =
        subtotal +
        tax -
        discount;
    ```
=== "JavaScript"

    ```js hl_lines="2-3"
    const total =
        subtotal +
        tax -
        discount;
    ```
=== "TypeScript"

    ```ts hl_lines="2-3"
    const total: number =
        subtotal +
        tax -
        discount;
    ```

### Parameter wrap

When breaking a multiline parameter list, each parameter should be placed on a
new line.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java hl_lines="2"
    void createUser(
        String name, String email, int age
    ) {}
    ```
=== "Groovy"

    ```groovy hl_lines="2"
    def createUser(
        String name, String email, int age
    ) {}
    ```
=== "Kotlin"

    ```kotlin hl_lines="2"
    fun createUser(
        name: String, email: String, age: Int
    ) {}
    ```
=== "C/C++"

    ```cpp hl_lines="2"
    void createUser(
        String name, String email, int age
    ) {}
    ```
=== "Python"

    ```python hl_lines="2"
    def create_user(
        name, email, age
    ):
        pass
    ```
=== "JavaScript"

    ```js hl_lines="2"
    function createUser(
        name, email, age
    ) {}
    ```
=== "TypeScript"

    ```ts hl_lines="2"
    function createUser(
        name: string, email: string, age: number
    ): void {}
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java hl_lines="2-4"
    void createUser(
        String name,
        String email,
        int age
    ) {}
    ```
=== "Groovy"

    ```groovy hl_lines="2-4"
    def createUser(
        String name,
        String email,
        int age,
    ) {}
    ```
=== "Kotlin"

    ```kotlin hl_lines="2-4"
    fun createUser(
        name: String,
        email: String,
        age: Int,
    ) {}
    ```
=== "C/C++"

    ```cpp hl_lines="2-4"
    void createUser(
        String name,
        String email,
        int age
    ) {}
    ```
=== "Python"

    ```python
    def create_user(
        name,
        email,
        age,
    ):
        pass
    ```
=== "JavaScript"

    ```js hl_lines="2-4"
    function createUser(
        name,
        email,
        age,
    ) {}
    ```
=== "TypeScript"

    ```ts hl_lines="2-4"
    function createUser(
        name: string,
        email: string,
        age: number,
    ): void {}
    ```

### Statement wrap

Compound statements are not allowed.

**:material-star-four-points-outline:{ #accent } Before**

=== "Java"

    ```java
    int x = 0; int y = 0;
    ```
=== "Groovy"

    ```groovy
    var x = 0; var y = 0
    ```
=== "Kotlin"

    ```kotlin
    val x = 0; val y = 0
    ```
=== "Python"

    ```python
    x = 0; y = 0
    ```
=== "JavaScript"

    ```js
    const x = 0; const y = 0;
    ```
=== "TypeScript"

    ```ts
    const x: number = 0; const y: number = 0;
    ```

**:material-star-four-points:{ #accent } After**

=== "Java"

    ```java
    int x = 0;
    int y = 0;
    ```
=== "Groovy"

    ```groovy
    var x = 0
    var y = 0
    ```
=== "Kotlin"

    ```kotlin
    val x = 0
    val y = 0
    ```
=== "Python"

    ```python
    x = 0
    y = 0
    ```
=== "JavaScript"

    ```js
    const x = 0;
    const y = 0;
    ```
=== "TypeScript"

    ```ts
    const x: number = 0;
    const y: number = 0;
    ```
