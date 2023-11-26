import { NextPage } from 'next';
import React from 'react';

type UserProps = {
    userData: string[];
};

const User: NextPage<UserProps> = ({ userData }) => {
    return (
        <div>
            <p>{JSON.stringify(userData)}</p>
        </div>
    );
};

export default User;
