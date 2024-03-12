import { useMediaQuery } from '@chakra-ui/react'
import React from 'react'
import Navbar from '../Components/Navbar/Navbar'
import BottomNavbar from '../Components/Navbar/BottomNavbar'
import PersonalInfoEdit from '../Components/Settings/PersonalInfoEdit'

const EditPersonalDetailsPage = () => {
    const [isLargerThan1280] = useMediaQuery('(min-width: 700px)')

    return (
        <>
            {isLargerThan1280 ? <Navbar /> : <BottomNavbar />}
            <PersonalInfoEdit />
        </>
    )
}

export default EditPersonalDetailsPage