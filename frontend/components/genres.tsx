import { NextPage } from 'next';
import React from 'react';
import { GenreDataProps } from '../pages/dashboard';
import { PieChart } from '@mui/x-charts/PieChart';

type Props = {
  genreData: GenreDataProps[]
}

const Genres: NextPage<Props> = ({ genreData }) => {
  const data = genreData.map((val, index) => {
    return { id: index, value: val.percentage, label: val.genre }
  })

  const color = ["#1f77b4", "#ff7f0e", "#2ca02c", "#d62728", "#9467bd", "#8c564b", "#e377c2", "#7f7f7f", "#bcbd22", "#17becf"]

  return (
    <PieChart
      colors={color}
      series={
        [{
          data,
          highlightScope: { faded: 'global', highlighted: 'item' },
          faded: { innerRadius: 30, additionalRadius: -30, color: 'gray' },
          arcLabel: "label",
          arcLabelMinAngle: 15,
        }
        ]}
      title='Genres'
      width={1024}
      height={1600}
    />
  );
};

export default Genres;
