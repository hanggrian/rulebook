import rulebook from 'rulebook-typescript-eslint';
import { type AssertThat, Asserter } from 'testing-js/src/typescript-asserters';

function assertThatAllRules(): AssertThat {
    return (code: string) => new Asserter(Object.values(rulebook.rules), code);
}

export default assertThatAllRules;
