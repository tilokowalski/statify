import Cookies from 'cookies';

import { CircularProgress } from "@mui/material";
import { GetServerSideProps, NextPage } from "next";
import { NextResponse } from 'next/server';

const spotifyClientId = process.env.NEXT_PUBLIC_SPOTIFY_CLIENT_ID;

const spotifyTokenEndpoint = "https://accounts.spotify.com/api/token";
const spotifyRedirectUri = 'http://localhost:3000/callback';

const Callback: NextPage = () => {
  return <CircularProgress />;
};

export default Callback;

export const getServerSideProps: GetServerSideProps = async ({ query, req, res }) => {
  const cookies = new Cookies(req, res);

  const token = await getToken(query, cookies);

  const accessToken = token.access_token;
  const expiresIn = token.expires_in;

  cookies.set('access_token', accessToken, {
    maxAge: expiresIn * 1000,
  });

  return {
    redirect: {
      destination: '/',
      permanent: false,
    },
  };

  return { props: {} };
};

interface TokenResponse {
  access_token: string;
  expires_in: number;
}

async function getToken(query: any, cookies: any): Promise<TokenResponse> {
  if (spotifyClientId === undefined) {
    throw new Error('Missing client ID');
  }

  const codeVerifier = cookies.get('code_verifier');

  if (codeVerifier === undefined) {
    throw new Error('Missing code verifier');
  }

  const code = query.code;

  const response = await fetch(spotifyTokenEndpoint, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: new URLSearchParams({
      client_id: spotifyClientId,
      grant_type: 'authorization_code',
      code,
      redirect_uri: spotifyRedirectUri,
      code_verifier: codeVerifier,
    }),
  });

  return await response.json();
}
