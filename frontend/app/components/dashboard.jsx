import currentToken from "../../utils/token.js"

async function getUserData() {
  const res = await fetch("https://api.spotify.com/v1/me", {
    method: 'GET',
    headers: {
      'Authorization': 'Bearer ' + currentToken.access_token
    },
  });

  if (!res.ok) {
    // This will activate the closest `error.js` Error Boundary
    throw new Error('Failed to fetch user data')
  }
  return { userData: await res.json() }
}


export default async function Dashboard() {
  const { userData } = await getUserData()

  return <p>{JSON.stringify(userData)}</p>;
}
