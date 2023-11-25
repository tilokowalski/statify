"use client"

import { Button } from '@mui/material';

export default function Login() {
  const redirectToSpotifyAuthorize = async () => {
    const response = await fetch('/api/spotifyAuth', {
      method: 'POST'
    });
    const { url } = await response.json();

    window.location.href = url;
  };
  return (
    <Button variant="contained" onClick={async () => {
      await redirectToSpotifyAuthorize();
    }}>Login via Spotify</Button>
  );
}
