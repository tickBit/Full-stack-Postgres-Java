import { useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import {FaSignInAlt, FaSignOutAlt, FaUser} from 'react-icons/fa';
import { useNavigate } from 'react-router';
import { Link } from 'react-router';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';

import { useAuth } from '../AuthContext';

function Header() {

  const navigate = useNavigate();

  const { isLoggedIn, logout, token } = useAuth();
  const { isSuccess, isLoading, pics } = useSelector((state) => state.pic);
  const [ canDelete, setCanDelete ] = useState(false);


  useEffect(() => {

    if (!isLoading && isSuccess) {
    
      if (pics.length === 0) {
        setCanDelete(true);
      } else {
        setCanDelete(false);
      }

    } else {
      setCanDelete(false);
    }
    

  }, [pics, isLoading, isSuccess]);

  // Before deleting user's account, check from state, if user has saved pictures;
  // if the user does, user won't be deleted (this would work without that check too, though)
  const handleDelete = async () => {
    
    if (canDelete) {
      try {
        const res = await axios.delete('http://localhost:8080/deleteme', {
          headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "multipart/form-data"
                },
          withCredentials: true },
        );

        if (res.data === undefined) {
          toast("Perhaps the PostgreSQL database isn't running");
          return;
        } else {
          toast(res.data);
          logout();
          navigate('/');
        }


      } catch (error) {
        toast("Deleting user failed. Perhaps there exists images by the user.");
      }
    }
        toast.error("Please delete your pictures first");
      
    
  }

  const onLogout = (e) => {
    console.log("Log out!");
    logout();
  }

  return (
    <div className='container'>

    <header className='header'>
          <div className='menu'>
          {!isLoggedIn ? (<>
          <Link to="/register">
            <span>Register</span>
          </Link>
          <Link to="/login">
            <span><FaSignInAlt /> Login</span>
          </Link>
          </>
          
          ): (<> <Link onClick={() => handleDelete()} >
            <span>Delete my account</span>
          </Link>
          <Link onClick={(e) => onLogout(e)}>
            <span >Logout</span>
          </Link>
         </>) }
          </div>

          <ToastContainer containerId="app-toasts" limit={1} newestOnTop />

          <h1>Image Gallery</h1>
          <p>Please notice, that the access token is set to last TWO MINUTES only!</p>
          
    </header>

    </div>
    
  );
}

export default Header