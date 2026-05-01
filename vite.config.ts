import { defineConfig, type UserConfig } from 'vite';

export default defineConfig({
    test: {
        include: ['src/__tests__/**/*.test.ts'],
        environment: 'jsdom',
        globals: true,
    },
} as UserConfig);
