module.exports = {
  pluginOptions: {
    i18n: {
      locale: 'pt_BR',
      fallbackLocale: 'en_US',
      localeDir: 'locales',
      enableInSFC: false
    }
  },
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8085',
        ws: true,
        changeOrigin: true
      },
      '/oauth': {
        target: 'http://localhost:8085',
        ws: true,
        changeOrigin: true
      },
      '/user-account': {
        target: 'http://localhost:8085',
        ws: true,
        changeOrigin: true
      }
    }
  }
}
