
import { useRouter } from 'next/router';
import { useEffect } from 'react';
import currentToken from '../utils/token';
import Cookies from 'universal-cookie';

const cookies = new Cookies();

const spotifyClientId = process.env.NEXT_PUBLIC_SPOTIFY_CLIENT_ID;

const spotifyTokenEndpoint = "https://accounts.spotify.com/api/token";
const spotifyRedirectUri = 'http://localhost:3000/callback';

async function getToken(code) {
    const codeVerifier = cookies.get('code_verifier');

    const response = await fetch(spotifyTokenEndpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            client_id: spotifyClientId,
            grant_type: 'authorization_code',
            code: code,
            redirect_uri: spotifyRedirectUri,
            code_verifier: codeVerifier,
        }),
    });

    return await response.json();
}

export default function Callback() {
    const router = useRouter();

    useEffect(() => {
        async function handleAuth() {
            const args = new URLSearchParams(window.location.search);
            const code = args.get('code');

            
            if (code) { 
                try {
                    const token = await getToken(code);

                    currentToken.save(token);
                    router.push('/dashboard');
                } catch (error) {
                    // TODO Show toast with error message
                    
                    console.error('Error during authentication:', error);
                    router.push('/');
                }
            } else {
                router.push('/');
            }
        }

        currentToken.isValid() ? router.push('/dashboard') : handleAuth();
    }, [router]);

    return null;
}