import React from 'react'
import FindRideLocation from '../Components/FindRide/FindRideLocation'
import { useMediaQuery } from '@chakra-ui/react'
import Navbar from '../Components/Navbar/Navbar'
import BottomNavbar from '../Components/Navbar/BottomNavbar'

const FindRidePage = () => {
    const [isLargerThan1280] = useMediaQuery('(min-width: 700px)')

    return (
        <>
            {isLargerThan1280 ? <Navbar /> : <BottomNavbar />}
            <FindRideLocation />
        </>
    )
}

export default FindRidePage