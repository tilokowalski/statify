'use client';

import { PieChart } from '@mui/x-charts';

export default function GenreChart({ data }) {

    console.log("Data:", data);
    console.log("Type of data:", typeof data);

    const chartData = data.genres.map((item, index) => ({
        value: (item[1] * 100).toFixed(2),
        label: item[0], id: index
    }));

    return (
        <PieChart
            series={[{
                data: chartData
            }]}
            width={400}
            height={200}
        />
    );
}
