public class ConditionalBranchLineWrapping {
    public ConditionalBranchLineWrapping() {
        switch (foo) {
            case 0:
                bar();
                break;
            case 1:
                baz();
                break;
        }
    }
}
