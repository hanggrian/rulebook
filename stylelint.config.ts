import { stylebookStylelint } from '@hanggrian/stylebook';
import stylisticStylelintPlugin from '@stylistic/stylelint-plugin';
import stylelintConfigRecommended from 'stylelint-config-recommended';
import type { Config } from 'stylelint';

export default {
    'plugins': [
        stylisticStylelintPlugin,
    ],
    'extends': [
        stylelintConfigRecommended,
        stylebookStylelint,
    ],
} satisfies Config;
