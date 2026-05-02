import rulebook from 'rulebook-eslint';
import { type AssertThat, Asserter } from 'testing/src/asserters';

function assertThatAllRules(): AssertThat {
    return (code: string) => new Asserter(Object.values(rulebook.rules), code);
}

export default assertThatAllRules;
