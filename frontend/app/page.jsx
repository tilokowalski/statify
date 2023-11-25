
'use client';

import { Button } from '@mui/material';
import { useEffect, useState } from 'react';

export default function HomePage() {
    const [isTokenValid, setIsTokenValid] = useState(false);

    useEffect(() => {
        const checkToken = async () => {
            const response = await fetch('/api/checkToken');
            const { isValid } = await response.json();

            setIsTokenValid(isValid);
        };

        checkToken();
    });

    if (isTokenValid) {
        window.location.href = '/dashboard';
    }

    const redirectToSpotifyAuthorize = async () => {
        const response = await fetch('/api/spotifyAuth', {
            method: 'POST'
        });
        const { url } = await response.json();
        
        window.location.href = url;
    };

    return (
        <Button variant="contained" onClick={redirectToSpotifyAuthorize}>Login via Spotify</Button>
    );
}