import React from 'react';
import './App.css';
import { Box } from '@chakra-ui/react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Signup from './Components/Signup/Signup';
import Login from './Components/Login/Login';
import Home from './Components/Home/Home';
import ForgotPasswordEmail from './Components/Login/ForgotPasswordEmail';
import ConfirmPassword from './Components/Login/ConfirmPassword';
import AccountEmailVerification from './Components/Signup/AccountEmailVerification';
import RedirectAfterAccVerify from './Components/Signup/RedirectAfterAccVerify';
import RiderRegistration from './Components/RiderRegistration/RiderRegistration';

function App() {
  return (
    <Box>
      <Router>
        <Routes>
          <Route element={<Login />} path="/login" />
          <Route element={<Signup />} path="/signup" />
          <Route element={<Home />} path="/" />
          <Route element={<AccountEmailVerification />} path="/account_email_verification" />
          <Route element={<RedirectAfterAccVerify />} path="/confirm_registration/:id/:email" />
          <Route element={<ForgotPasswordEmail />} path="/forgot_password" />
          <Route element={<ConfirmPassword />} path="/confirm_password/:token/:id" />
          <Route element={<RiderRegistration />} path="/rider_registration" />
        </Routes>
      </Router>
    </Box>
  );
}

export default App;
