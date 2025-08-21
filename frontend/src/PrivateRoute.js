import { useAuth } from './AuthContext';
import PleaseLogin from './components/PleaseLogin';

const PrivateRoute = ({ children }) => {
  const { isLoggedIn } = useAuth();

  return isLoggedIn ? children : <PleaseLogin />;
};

export default PrivateRoute;
