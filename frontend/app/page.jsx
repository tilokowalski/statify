import currentToken from "../utils/token.js"
import Login from './components/login.jsx';
import Overview from './components/overview.jsx';

export default function Home() {
  if (!currentToken.isValid()) {
      return <Login/>;
  }

  return <Overview/>;
}
