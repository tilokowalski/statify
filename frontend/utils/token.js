
import { cookies } from 'next/headers'

const currentToken = {
    get cookieStore() {
        return cookies();
    },

    get access_token() {
        return this.cookieStore.get('access_token') ? this.cookieStore.get('access_token').value : null;
    },
    
    get refresh_token() {
        return this.cookieStore.get('refresh_token') ? this.cookieStore.get('refresh_token').value : null;
    },

    save: function (response) {
        const { access_token, refresh_token, expires_in } = response;
        const expiryDate = new Date(new Date().getTime() + expires_in * 1000);

        this.cookieStore.set('access_token', access_token, { expires: expiryDate });
        this.cookieStore.set('refresh_token', refresh_token, { expires: expiryDate });
    },

    isValid: function () {
        return this.cookieStore.get('access_token') && this.cookieStore.get('refresh_token');
    },
};

export default currentToken;
