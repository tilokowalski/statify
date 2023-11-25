
import { useRouter } from 'next/router';
import { useEffect, useState } from 'react';
import currentToken from '../utils/token';

async function getUserData() {
    const response = await fetch("https://api.spotify.com/v1/me", {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + currentToken.access_token
        },
    });

    return await response.json();
}

export default function Dashboard() {
    const [userData, setUserData] = useState(null);

    const router = useRouter();

    useEffect(() => {

        if (!currentToken.isValid()) {
            router.push('/');
        }

        getUserData().then(data => {
            setUserData(data);
        });
    }, [router]);

    return null;
}