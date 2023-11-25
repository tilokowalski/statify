
'use client';

import { useEffect, useState } from 'react';

export default function Dashboard() {
    const [userData, setUserData] = useState(null);

    useEffect(() => {
        const checkToken = async () => {
            const response = await fetch('/api/checkToken');
            const { isValid } = await response.json();

            if (!isValid) {
                window.location.href = '/';
            }
        };

        checkToken();

        const fetchUserData = async () => {
            const response = await fetch('/api/userData');
            const { userData } = await response.json();

            setUserData(userData);
        }

        fetchUserData();
    });

    return <p>{ JSON.stringify(userData) }</p>;
}