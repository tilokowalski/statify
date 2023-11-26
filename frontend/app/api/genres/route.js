
'use server';

import { Surreal } from 'surrealdb.js';

const DB_ENDPOINT = "ws://127.0.0.1:8000/rpc"
const DB_NAMESPACE = "statify";
const DB_DATABASE = "statify";

const DB = new Surreal();

export async function POST(request) {
    const requestBody = await request.json();

    // const id = requestBody.userId;
    const id = 1; // TODO Remove after testing;

    try {
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
    } catch (error) {
        console.error('Error connecting to SurrealDB', error);
        return Response.json({ error: 'Error connecting to SurrealDB' }, { status: 400 });
    }

    try {
        const result = await DB.query(`
            LET $genres = SELECT VALUE array::flatten(->listens->track.artists.genres) as genres FROM ONLY $user_id;
            SELECT * FROM array::distinct($genres);
        `, { user_id: "user:" + id });

        const genre_percentages = [];

        const promises = result[1].map(async (genre) => {
            let innerResult = await DB.query(`
                LET $genres = SELECT VALUE array::flatten(->listens->track.artists.genres) as genres FROM ONLY $user_id;
                
                SELECT 
                    array::len(genre_filter) as number_of_tracks_per_genre,
                    <float> array::len(genre_filter) / count(genres) as percentage,
                    count(genres) as total 
                FROM ONLY 
                    {genre_filter: array::filter_index($genres, $genre_to_search), genres: $genres};
            `, { genre_to_search: genre, user_id: "user:" + id });

            return [genre, innerResult[1].percentage];
        });

        return Promise.all(promises).then((percentages) => {
            genre_percentages.push(...percentages);
            return Response.json({ genres: genre_percentages }, { status: 200 });
        });
    } catch (error) {
        console.error('Error querying SurrealDB', error);
        return Response.json({ error: 'Error querying SurrealDB' }, { status: 400 });
    }
}
