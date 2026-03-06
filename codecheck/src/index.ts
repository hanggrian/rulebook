import methodParameterNameRule from './method-parameter-name.js';
import ruleHasDocumentationRule from './rule-has-documentation.js';
import ruleHasSampleRule from './rule-has-sample.js';

export default {
    rules: {
        'method-parameter-name': methodParameterNameRule,
        'rule-has-documentation': ruleHasDocumentationRule,
        'rule-has-sample': ruleHasSampleRule,
    },
    configs: {
        default: {
            rules: {
                '@codecheck/method-parameter-name': 'error',
                '@codecheck/rule-has-documentation': 'error',
                '@codecheck/rule-has-sample': 'error',
            },
        },
    },
};
