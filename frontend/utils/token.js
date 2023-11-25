
import Cookies from 'universal-cookie';

const cookies = new Cookies();

const currentToken = {
    get access_token() {
        return cookies.get('access_token');
    },
    
    get refresh_token() {
        return cookies.get('refresh_token');
    },

    save: function (response) {
        const { access_token, refresh_token, expires_in } = response;
        const expiryDate = new Date(new Date().getTime() + expires_in * 1000);

        cookies.set('access_token', access_token, { path: '/', expires: expiryDate });
        cookies.set('refresh_token', refresh_token, { path: '/', expires: expiryDate });
    },

    isValid: function () {
        return !!cookies.get('access_token');
    },
};

export default currentToken;
