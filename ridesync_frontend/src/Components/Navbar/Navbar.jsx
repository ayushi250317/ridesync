import React from 'react'
import { Box, Flex, Image, PopoverArrow } from "@chakra-ui/react"
import { Link } from 'react-router-dom'
import { IoSettingsSharp } from "react-icons/io5";
import {
    Popover,
    PopoverTrigger,
    PopoverContent,
    PopoverHeader,
    PopoverBody,
    PopoverCloseButton,
} from '@chakra-ui/react'
import { FaUser } from 'react-icons/fa';

const Navbar = () => {
    return (
        <Box borderBottom="1px solid lightgray" >

            <Flex w="90%" m="auto" p="4" justifyContent="space-between" alignItems="center" >
                <Box>
                    <Image src='/ridesyncNewLogo.png' w="18%"></Image>
                </Box>
                <Flex fontSize="xl" >
                    <Box mx="5">
                        <Link to="/">
                            Home
                        </Link>
                    </Box>
                    <Box mx="5">
                        <Link to="/activity">
                            Activity
                        </Link>
                    </Box>
                    <Box mx="5" position="relative">
                        {/* <Box className='notification-number-uppernav'><Text>5</Text> </Box> */}
                        <Link to="/notifications">
                            Notifications
                        </Link>
                    </Box>
                    <Box mx="5" backgroundColor="black" color="white" px={["", "", "", "4"]} borderRadius="xl" pb={["", "", "", "1"]}>
                        <Link to="/login" onClick={() => localStorage.removeItem("loggedInUserDetails")}>
                            Log out
                        </Link>
                    </Box>
                    <Box mx="5">
                        <Link>
                            <Popover>
                                <PopoverTrigger>
                                    <Box
                                        tabIndex='0'
                                        role='button'
                                        aria-label='Some box'
                                        // p={5}
                                        // w='120px'
                                        // bg='gray.300'
                                        children='Click'
                                    >
                                        <IoSettingsSharp size="30px" />
                                    </Box>

                                </PopoverTrigger>
                                <PopoverContent >
                                    <PopoverHeader fontWeight='semibold'>Settings</PopoverHeader>
                                    <PopoverArrow />
                                    <PopoverCloseButton />
                                    <PopoverBody>
                                        <Flex flexDir="column" fontSize="xl">
                                            <Flex justifyContent="start" alignItems="center" my="1">
                                                <FaUser size="25px" />
                                                <Box mx="2">

                                                    <Link to="/editpersonalinfo" >Personal Details</Link>
                                                </Box>
                                            </Flex>
                                        </Flex>
                                    </PopoverBody>
                                </PopoverContent>
                            </Popover>
                        </Link>
                    </Box>
                </Flex>
            </Flex>
        </Box>

    )
}

export default Navbar