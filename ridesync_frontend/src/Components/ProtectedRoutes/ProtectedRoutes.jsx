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
    if (location.pathname === '/add_ride') {
        const hasDrivingLicense = loggedInUserInfo.documents.some(doc => doc.documentType === 'driving_license');
        console.log(loggedInUserInfo.vehicles.length == 0)
        if ((!hasDrivingLicense) || loggedInUserInfo.vehicles.length === 0) {
            return <Navigate to="/rider_registration" state={{ from: location }} />;
        }
    }

    return <Outlet />


}

export default ProtectedRoutes