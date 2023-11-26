CREATE user:1 SET name = 'Tilo';
CREATE user:2 SET name = 'Josh';
CREATE user:3 SET name = 'Gero';
CREATE user:4 SET name = 'Ludi';

CREATE artist:1 SET genres = ["hiphop", "rap"];
CREATE artist:2 SET genres = ["techno"];
CREATE artist:3 SET genres = ["rap"];
CREATE artist:4 SET genres = ["rock"];
CREATE artist:5 SET genres = ["hiphop"];
CREATE artist:6 SET genres = ["hiphop"];
CREATE artist:7 SET genres = ["rap", "hiphop"];

CREATE track:1 SET title = 'CL500', artists = [artist:1];
CREATE track:2 SET title = 'Alle meine Entchen', artists = [artist:2];
CREATE track:3 SET title = 'Gangnam Style', artists = [artist:3];
CREATE track:4 SET title = 'Boomerang', artists = [artist:4,artist:6];
CREATE track:5 SET title = 'Sonnenbankflavour', artists = [artist:5];
CREATE track:6 SET title = 'Pimmel', artists = [artist:6];
CREATE track:7 SET title = 'Roller', artists = [artist:7];

RELATE user:1->listens->track:1 SET played_at=time::now();
RELATE user:2->listens->track:2 SET played_at=time::now();
RELATE user:3->listens->track:3 SET played_at=time::now();
RELATE user:4->listens->track:4 SET played_at=time::now();
RELATE user:1->listens->track:5 SET played_at=time::now();
RELATE user:2->listens->track:6 SET played_at=time::now();
RELATE user:3->listens->track:7 SET played_at=time::now();

# query for genre percentage
LET $genres = SELECT VALUE array::flatten(->listens->track.artists.genres) as genres FROM ONLY user:1;

SELECT array::len(genre_filter) as number_of_tracks_per_genre, <float> array::len(genre_filter) / count(genres) as percentage FROM ONLY {genre_filter: array::filter_index($genres, "hiphop"), genres: $genres};

