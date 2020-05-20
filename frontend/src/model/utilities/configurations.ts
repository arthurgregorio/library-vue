export const userSessionKey = '__l1brary_s3ss10n'
export const apiBaseUrl = process.env.VUE_APP_API_URL || 'http://localhost:8080'
export const apiClientId = process.env.VUE_APP_CLIENT_ID || 'library-web'
export const apiClientSecret = process.env.VUE_APP_CLIENT_SECRET || 'library-web'

export const authorities = {
  admin: 'ADMINISTRATOR',
  librarian: 'LIBRARIAN'
}

export default { userSessionKey, apiBaseUrl, apiClientId, apiClientSecret, authorities }
