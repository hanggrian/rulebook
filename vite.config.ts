import { defineConfig, UserConfig } from 'vite';

export default defineConfig({
    test: {
        include: ['src/__tests__/**/*.ts'],
        environment: 'jsdom',
        globals: true,
    },
} as UserConfig);
