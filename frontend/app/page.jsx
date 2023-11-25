import { Button } from '@mui/material';
import currentToken from "../utils/token.js"
import Login from './components/login.jsx';
import Dashboard from './components/dashboard.jsx';

export default function HomePage() {
  const isValid = currentToken.isValid();

  if (!isValid) {
    return (<Login></Login>);
  }
  return (<Dashboard></Dashboard>)
}
