import Genres from "../components/genres";
import User from "../components/user";
import { Surreal } from 'surrealdb.js';
import { GetServerSideProps, NextPage } from "next";
import Cookies from "cookies";
import { RawQueryResult } from "surrealdb.js/script/types";

const DB_ENDPOINT = "ws://127.0.0.1:8000/rpc"
const DB_NAMESPACE = "statify";
const DB_DATABASE = "statify";

const DB = new Surreal();

type DashboardProps = {
  userData: any;
  genreData: GenreDataProps[];
};

const Dashboard: NextPage<DashboardProps> = props => {
  return (
    <div>
      <User userData={props.userData} />
      <Genres genreData={props.genreData} />
    </div>
  );
};

export default Dashboard;

export const getServerSideProps: GetServerSideProps<DashboardProps> = async ({ req, res }) => {
  const cookies = new Cookies(req, res);

  const accessToken = cookies.get('access_token');

  if (accessToken === undefined) {
    return {
      redirect: {
        destination: '/',
        permanent: false,
      },
    };
  }

  const userData = await fetchUserData(accessToken);

  const userId = userData.id;

  await registerUser(userId, accessToken);

  const genreData = await fetchGenreData(userId);

  return { props: { userData, genreData } };
};

async function fetchUserData(accessToken: string | undefined) {
  const responseUserData = await fetch("https://api.spotify.com/v1/me", {
    method: 'GET',
    headers: {
      'Authorization': 'Bearer ' + accessToken
    },
  });

  if (!responseUserData.ok) {
    throw new Error('Failed to fetch user data');
  }

  return await responseUserData.json();
}

async function registerUser(userId: string, accessToken: string) {
  const response = await fetch("http://localhost:8080/process/" + userId, {
    method: 'POST',
    headers: {
      'Authorization': accessToken
    },
  });

  if (response.status != 204) {
    throw new Error('Failed to register user');
  }
}


export type GenreDataProps = {
  genre: string;
  percentage: number;
}

async function fetchGenreData(userId: string): Promise<GenreDataProps[]> {
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
  } catch (err) {
    console.error("Failed to connect to database", err);
  }

  try {
    const genres = await DB.query<[string]>(`
            SELECT VALUE array::group(->listens->track.artists.genres) FROM ONLY $user_id;
        `, { user_id: "user:" + userId });

    const genreData: GenreDataProps[] = [];

    const promises = genres.map(async (genre: string) => {
      let result = await DB.query<[{ number_of_tracks_per_genre: number, percentage: number }]>(`
                SELECT 
                    array::len(genre_filter) as number_of_tracks_per_genre,
                    <float> array::len(genre_filter) / count(genres) as percentage,
                    count(genres) as total 
                FROM ONLY 
                    {genre_filter: array::filter_index($genres, $genre_to_search), genres: $genres};
            `, { genre_to_search: genre, user_id: "user:" + userId, genres: genres });


      return { genre: genre, percentage: result[0].percentage };
    });

    return Promise.all(promises).then((percentages) => {
      genreData.push(...percentages);
      return genreData;
    });
  } catch (err) {
    console.error("Failed to query database", err);
    throw err;
  }
}

