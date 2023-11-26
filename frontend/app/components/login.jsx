
'use client';

import { useState } from 'react';
import { Button } from '@mui/material';

const baseUrl = process.env.NEXT_PUBLIC_APPLICATION_BASE_URL;

export default function Login() {
    const [isRedirecting, setIsRedirecting] = useState(false);

    const redirectToSpotifyAuthorize = async () => {
        setIsRedirecting(true);

        try {
            const response = await fetch(baseUrl + '/api/spotifyAuth', {
                method: 'POST'
            });
            const { url } = await response.json();

            window.location.href = url;
        } catch (error) {
            console.error('Error redirecting to Spotify:', error);
            setIsRedirecting(false);
        }
    };

    return (
        <Button 
            variant="contained" 
            onClick={redirectToSpotifyAuthorize}
            disabled={isRedirecting}
        >
            {isRedirecting ? 'Redirecting...' : 'Login via Spotify'}
        </Button>
    );
}
