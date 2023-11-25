
'use server';

import currentToken from '../../../utils/token';

export async function GET() {
    const response = await fetch("https://api.spotify.com/v1/me", {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + currentToken.access_token
        },
    });

    return Response.json({ userData: await response.json() });
}