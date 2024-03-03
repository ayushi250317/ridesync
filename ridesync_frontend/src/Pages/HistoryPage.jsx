import { Box, useMediaQuery } from '@chakra-ui/react'
import React from 'react'
import History from '../Components/History/History'
import Navbar from '../Components/Navbar/Navbar'
import BottomNavbar from '../Components/Navbar/BottomNavbar'

const HistoryPage = () => {
    const [isLargerThan1280] = useMediaQuery('(min-width: 700px)')

    return (
        <Box>
            {isLargerThan1280 ? <Navbar /> : <BottomNavbar />}
            <History />
        </Box>
    )
}

export default HistoryPage