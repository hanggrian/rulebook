public class IfStatementFlattening {
    public IfStatementFlattening() {
        if (true) {
            bar()
            baz()
        } else {
            bar()
        }
    }
}
