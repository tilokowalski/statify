
'use server';

import { cookies } from 'next/headers';
import currentToken from '../../../utils/token';
import { redirect } from 'next/navigation';

const spotifyClientId = process.env.NEXT_PUBLIC_SPOTIFY_CLIENT_ID;

const spotifyTokenEndpoint = "https://accounts.spotify.com/api/token";
const spotifyRedirectUri = 'http://localhost:3000/callback';

export async function POST(request) {
  const searchParams = request.nextUrl.searchParams;
  const code = searchParams.get('code');

  if (!code) return Response.json({ error: 'No code provided' }, { status: 400 });

  try {
    const token = await getToken(code);

    if (!token.access_token) throw new Error('No token returned');

    currentToken.save(token);

    const isValid = currentToken.isValid();

    if (!isValid) throw new Error('Invalid token');

    redirect("/")
  } catch (error) {
    return Response.json({ error: error }, { status: 400 });
  }
}

async function getToken(code) {
  const codeVerifier = cookies().get('code_verifier').value;

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
