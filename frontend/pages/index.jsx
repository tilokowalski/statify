import { useState } from 'react';
import { Surreal } from 'surrealdb.js';

const db = new Surreal();

export async function getServerSideProps(context) {

  let genreData = null;

  try {
		// Connect to the database
		await db.connect('http://127.0.0.1:8000/rpc', {
			// Set the namespace and database for the connection
			namespace: 'statify',
			database: 'statify',

			// Set the authentication details for the connection
			// auth: {
			// 	namespace: 'test',
			// 	database: 'test',
			// 	scope: 'user',
			// 	username: 'info@surrealdb.com',
			// 	password: 'my-secret-password',
			// },
		});

    // Updated query to calculate genre percentages
    const result = await db.query(`
    LET $total = (SELECT count() AS count FROM (SELECT ->listens->track.artist.genre FROM user:xyz));
    SELECT genre, COUNT(*) * 100 / $total.count AS percentage FROM (
      SELECT ->listens->track.artist.genre AS genre FROM user:xyz
    ) GROUP BY genre;
  `);

  genreData = result.map(item => ({
    genre: item.genre,
    percentage: item.percentage.toFixed(2)
  }));

    console.log(genreData)

	} catch (e) {
		console.error('ERROR', e);
	}
  return {
    props: {
      genreData
    },
  };
}

export default function HomePage({ genreData }) {
  const [likes, setLikes] = useState(0);

  function handleClick() {
    setLikes(likes + 1);
  }

  return (
    <div>
      <ul>
        {/* {listensData.genres.map((genre) => (
          <li key={genre}>{genre}</li>
        ))} */}
      </ul>

      <button onClick={handleClick}>Like ({likes})</button>
    </div>
  );
}