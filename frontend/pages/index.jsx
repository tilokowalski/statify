import { useState } from 'react';
import { Surreal } from 'surrealdb.js';

const DB = new Surreal();

const DB_NAMESPACE = 'statify';
const DB_DATABASE = 'statify';

const DB_ENDPOINT = 'ws://127.0.0.1:8000/rpc';

export async function getServerSideProps(context) {

    let dbResult = null;

    try {
        await DB.connect(DB_ENDPOINT, {
            namespace: DB_NAMESPACE,
            database: DB_DATABASE,

            // auth: {
            //   	namespace: 'test',
            //   	database: 'test',
            //   	scope: 'user',
            //   	username: 'info@surrealdb.com',
            //   	password: 'my-secret-password'
            // }
        });
    } catch (e) {
        console.error('Error connecting to SurrealDB', e);
    }

    try {
        const result = await DB.query(`
            LET $total = (SELECT count() AS count FROM (SELECT ->listens->track.artist.genre FROM user:xyz));

            SELECT genre, COUNT(*) * 100 / $total.count AS percentage FROM (
                SELECT ->listens->track.artist.genre AS genre FROM user:xyz
            ) GROUP BY genre;
        `);

        dbResult = result.map(item => ({
            genre: item.genre,
            percentage: item.percentage.toFixed(2)
        }));
    } catch (e) {
        console.error('Error querying SurrealDB', e);
    }

    return {
        props: {
            dbResult
        }
    };
}

export default function HomePage({ dbResult }) {
    return (
        <ul>
            {dbResult.genres.map((genre) => (
                <li key={genre}>{genre}</li>
            ))}
        </ul>
    );
}