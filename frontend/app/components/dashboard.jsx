
'use server';

import currentToken from "../../utils/token.js"
import { Drawer, List, ListItem, Typography } from '@mui/material';
import User from "./user.jsx";

async function getUserData() {
    const res = await fetch("https://api.spotify.com/v1/me", {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + currentToken.access_token
        },
    });

    if (!res.ok) {
        // This will activate the closest `error.js` Error Boundary
        throw new Error('Failed to fetch user data')
    }

    return { userData: await res.json() }
}

export default async function Dashboard() {
    const { userData } = await getUserData()

    return (
        <div style={{ 
            display: 'flex', 
            justifyContent: 'center', 
            alignItems: 'center', 
            height: '90vh' 
        }}
        >
            <User userData={userData} />
        </div>
    );
}
