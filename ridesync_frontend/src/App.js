import React from 'react';
import './App.css';
import { Box } from '@chakra-ui/react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Signup from './Components/Signup/Signup';
import Login from './Components/Login/Login';
import Home from './Components/Home/Home';
import ForgotPasswordEmail from './Components/Login/ForgotPasswordEmail';
import ConfirmPassword from './Components/Login/ConfirmPassword';

function App() {
  return (
    <Box>
      <Router>
        <Routes>
          <Route element={<Login />} path="/login" />
          <Route element={<Signup />} path="/signup" />
          <Route element={<Home />} path="/" />
          <Route element={<ForgotPasswordEmail />} path="/forgot_password" />
          <Route element={<ConfirmPassword />} path="/confirm_password/:token/:id" />
        </Routes>
      </Router>
    </Box>
  );
}

export default App;
