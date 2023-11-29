<h1 align="center">
    Statify - Spotify Analytics Platform
</h1>

<p align="center">
    This project is a web platform for Spotify analytics, designed to provide users with insights into their listening habits. The platform offers features like top listened genres, artists, and a listening history calendar. Development started in November 2023 as project idea for a <a href="https://github.com/tilokowalski/hackathon-01">private hackathon</a>.
</p>

## Technologies Used

- **Backend:** Java with Quarkus
- **Frontend:** Next.js (React)
- **Database:** SurrealDB

## Architecture

- **Backend (Java/Quarkus):** Responsible for write operations to SurrealDB. It fetches user data from the Spotify API and writes to the database.
- **Frontend (Next.js/React):** Handles all read operations directly through the SurrealDB endpoint, displaying data in a user-friendly format.

## Directory Structure

- `/backend` - Contains Java backend files.
- `/frontend` - Contains Next.js frontend files.
- `/surrealdb` - Includes SurrealDB setup files.

## Setup and Installation

1. Clone the repository: `git clone git@github.com:tilokowalski/statify.git`
2. Navigate to the project directory: `cd statify`
3. Create environment variable files named `.env.local` in `frontend` and `surrealdb`, comply to the respective structure from the example `.env` files.

4. Run the Docker container for the development database:
    ```
    cd surrealdb
    ./start.sh
    ```
5. Set up the backend:
    ```
    cd backend
    ./mvnw quarkus:dev
    ```
6. Set up the frontend:
    ```
    cd frontend
    npm run dev
    ```

The application is now running on `localhost:3000`.

## License
This project is licensed under Apache 2.0 - see [LICENSE](LICENSE) for details.
