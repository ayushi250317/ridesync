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
import RiderRegistration from './Components/PostRide/RiderRegistration';
import AddRide from './Components/PostRide/AddRide';
import FindRidePage from './Pages/FindRidePage';
import NotificationPage from './Pages/NotificationPage';
import ProtectedRoutes from './Components/ProtectedRoutes/ProtectedRoutes';
import NotFound from './Pages/NotFound';
import LiveLocationTracking from './Components/LiveLocationTracking/LiveLocationTracking';
import EditPersonalDetailsPage from './Pages/EditPersonalDetailsPage';
import Activity from './Components/Activity/Activity';
import RideInfo from './sharedComponent/RideInfo/RideInfo';

function App() {
  return (
    <Box>
      <Router>
        <Routes>
          <Route element={<ProtectedRoutes />}>
            <Route element={<RiderRegistration />} path="/rider_registration" />
            <Route element={<FindRidePage />} path="/find_ride" />
            <Route element={<AddRide />} path="/add_ride" />
            <Route element={<Activity />} path="/activity" />
            <Route element={<RideInfo />} path="/ride_info" />
            <Route element={<NotificationPage />} path="/notifications" />
            <Route element={<LiveLocationTracking />} path="/livelocationtracking" />
            {/* <Route element={<SelectLocation />} path="/find_ride" /> */}
            <Route element={<Home />} path="/" />
            <Route element={<EditPersonalDetailsPage />} path="/editpersonalinfo" />
            <Route element={<FindRidePage />} path="/find_ride" />
          </Route>
          <Route element={<RedirectAfterAccVerify />} path="/confirm_registration/:id/:email" />
          <Route element={<AccountEmailVerification />} path="/account_email_verification" />
          <Route element={<ForgotPasswordEmail />} path="/forgot_password" />
          <Route element={<ConfirmPassword />} path="/confirm_password/:token/:id" />
          <Route element={<Login />} path="/login" />
          <Route element={<Signup />} path="/signup" />
          <Route path="/notFound" element={<NotFound />} />
          <Route path="*" element={<NotFound />} />


        </Routes>
      </Router>
    </Box>
  );
}

export default App;
