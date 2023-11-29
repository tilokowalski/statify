import { NextPage } from 'next';
import React from 'react';
import { GenreDataProps } from '../pages/dashboard';
import { PieChart } from '@mui/x-charts/PieChart';

type Props = {
  genreData: GenreDataProps[]
}

const Genres: NextPage<Props> = ({ genreData }) => {
  const chartData = genreData.map((val, index) => {
    return { id: index, value: val.percentage, label: val.genre }
  })
  return (
    <div>
      <p>{JSON.stringify(genreData)}</p>
      <PieChart
        series={
          [{
            data: chartData
          }
          ]}
        width={1024}
        height={1600}
      />
    </div>
  );
};

export default Genres;
