import Cookies from 'cookies';

import { Link } from '@mui/material';
import { NextResponse } from 'next/server';
import { GetServerSideProps, NextPage } from 'next';

const spotifyClientId = process.env.NEXT_PUBLIC_SPOTIFY_CLIENT_ID;

const spotifyAuthEndpoint = "https://accounts.spotify.com/authorize";
const spotifyRedirectUri = 'http://localhost:3000/callback';
const spotifyScope = 'user-read-private user-read-email user-read-recently-played';

export const getServerSideProps: GetServerSideProps = async ({ req, res }) => {
    const possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    const randomValues = crypto.getRandomValues(new Uint8Array(64));
    const randomString = randomValues.reduce((acc, x) => acc + possible[x % possible.length], "");

    const codeVerifier = randomString;
    const data = new TextEncoder().encode(codeVerifier);
    const hashed = await crypto.subtle.digest('SHA-256', data);

    const codeChallengeBase64 = btoa(String.fromCharCode(...new Uint8Array(hashed)))
        .replace(/=/g, '')
        .replace(/\+/g, '-')
        .replace(/\//g, '_');

    const cookies = new Cookies(req, res);

    cookies.set('code_verifier', codeVerifier);

    const authUrl = new URL(spotifyAuthEndpoint)

    if (spotifyClientId === undefined) {
        throw new Error('Spotify Client ID is undefined');
    }

    const params = {
        response_type: 'code',
        client_id: spotifyClientId,
        scope: spotifyScope,
        code_challenge_method: 'S256',
        code_challenge: codeChallengeBase64,
        redirect_uri: spotifyRedirectUri,
    };

    authUrl.search = new URLSearchParams(params).toString();

    return { props: { authUrl: authUrl.toString() } };
}

type LoginProps = {
    authUrl: string;
}

const Login: NextPage<LoginProps> = ({ authUrl }) => {
    return (
        <div>
            <Link href={authUrl}>Login with Spotify</Link>
        </div>
    );
}

export default Login;
