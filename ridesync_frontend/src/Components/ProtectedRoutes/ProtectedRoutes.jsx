import React from 'react'
import { Navigate, Outlet, useLocation } from "react-router-dom"
const ProtectedRoutes = () => {
    const location = useLocation()


    const loggedInUserInfo = JSON.parse(
        localStorage.getItem("loggedInUserDetails")
    );

    if (!loggedInUserInfo) {
        return <Navigate to="/login" state={{ from: location }} />
    }
    return <Outlet />


}

export default ProtectedRoutes