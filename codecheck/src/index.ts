import methodParameterNameRule from './method-parameter-name.js';
import urlInDocumentationRule from './url-in-documentation.js';

export default {
    rules: {
        'method-parameter-name': methodParameterNameRule,
        'url-in-documentation': urlInDocumentationRule,
    },
    configs: {
        default: {
            rules: {
                '@codecheck/method-parameter-name': 'error',
                '@codecheck/url-in-documentation': 'error',
            },
        },
    },
};
