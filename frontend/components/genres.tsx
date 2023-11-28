import { NextPage } from 'next';
import React from 'react';
import { GenreDataProps } from '../pages/dashboard';

type Props = {
  genreData: GenreDataProps[]
}

const Genres: NextPage<Props> = ({ genreData }) => {
  return (
    <div>
      <p>{JSON.stringify(genreData)}</p>
    </div>
  );
};

export default Genres;
