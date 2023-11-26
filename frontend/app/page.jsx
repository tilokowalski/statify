import currentToken from "../utils/token.js"
import Login from './components/login.jsx';
import Dashboard from './components/dashboard.jsx';

export default function Home() {
  if (!currentToken.isValid()) {
      return <Login/>;
  }

  return <Dashboard/>;
}
