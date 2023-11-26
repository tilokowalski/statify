'use server';

import currentToken from "../../utils/token.js"
import { Drawer, List, ListItem, Typography } from '@mui/material';
import User from "./user.jsx";
//import { PieChart } from '@mui/x-charts/PieChart';
import { Surreal } from 'surrealdb.js';

async function getUserData() {
  const DB = new Surreal();
  const DB_ENDPOINT = "http://127.0.0.1:8000/rpc"
  const DB_NAMESPACE = "statify";
  const DB_DATABASE = "statify";

  await DB.connect(DB_ENDPOINT, {
    namespace: DB_NAMESPACE,
    database: DB_DATABASE,

    auth: {
      namespace: 'statify',
      database: 'statify',
      username: 'root',
      password: 'root'
    }
  });

  try {
    let result = await DB.query(`
LET $genres = SELECT VALUE array::flatten(->listens->track.artists.genres) as genres FROM ONLY $user_id;

SELECT * FROM array::distinct($genres);
        `, { user_id: "user:1" });
    result[1].forEach(async (genre) => {

      let result = await DB.query(`
LET $genres = SELECT VALUE array::flatten(->listens->track.artists.genres) as genres FROM ONLY $user_id;

SELECT 
array::len(genre_filter) as number_of_tracks_per_genre,
<float> array::len(genre_filter) / count(genres) as percentage,
count(genres) as total 
FROM ONLY 
{genre_filter: array::filter_index($genres, $genre_to_search), genres: $genres};
`, { genre_to_search: genre, user_id: "user:1" })

      return result[1];
    });

  } catch (e) {
    console.error('Error querying SurrealDB', e);
  }

}

export default async function GenrePieChart() {
  const { userData } = await getUserData()

  return (<div></div>)
}
