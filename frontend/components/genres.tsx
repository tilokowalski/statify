import { NextPage } from 'next';
import React from 'react';

type GenresProps = {
    genreData: string[];
};

const Genres: NextPage<GenresProps> = ({ genreData }) => {
    return (
        <div>
            <p>{JSON.stringify(genreData)}</p>
        </div>
    );
};

export default Genres;
