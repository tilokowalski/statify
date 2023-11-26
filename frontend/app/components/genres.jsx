
'use server';

import dynamic from 'next/dynamic';

const GenreChart = dynamic(() => import('./genre-chart'), {
  ssr: false,
});

const baseUrl = process.env.NEXT_PUBLIC_APPLICATION_BASE_URL;

export default async function Genres({ userId }) {

    const response = await fetch(baseUrl + '/api/genres', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ userId }),
    });

    const { genres } = await response.json();

    return (
        <GenreChart data={{ genres }} />
    );
}
