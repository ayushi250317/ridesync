import { Box, useMediaQuery } from '@chakra-ui/react'
import React from 'react'
import Notification from '../Components/Notification/Notification'
import Navbar from '../Components/Navbar/Navbar'
import BottomNavbar from '../Components/Navbar/BottomNavbar'

const NotificationPage = () => {
    const [isLargerThan1280] = useMediaQuery('(min-width: 700px)')

    return (
        <Box>
            {isLargerThan1280 ? <Navbar /> : <BottomNavbar />}

            <Notification />
        </Box>)
}

export default NotificationPage