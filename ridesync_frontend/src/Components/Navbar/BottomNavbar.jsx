import { Box, Center, Flex, Text, useDisclosure } from '@chakra-ui/react'
import React from 'react'
import { IoNotifications, IoSettingsSharp } from "react-icons/io5";
import { FaCar, FaHistory, FaHome, FaUser, FaUserEdit } from "react-icons/fa";
import { RiLogoutCircleRFill } from "react-icons/ri";
import {
    Drawer,
    DrawerBody,
    DrawerHeader,
    DrawerOverlay,
    DrawerContent,
    DrawerCloseButton,
} from '@chakra-ui/react'
import { IoMdDocument } from "react-icons/io";

import { Link } from 'react-router-dom';
const BottomNavbar = () => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return (
        <Box className='navbar' borderTop="1px solid lightgray">
            <Drawer
                isOpen={isOpen}
                placement='right'
                onClose={onClose}
            // finalFocusRef={btnRef}
            >
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>Settings</DrawerHeader>
                    <DrawerBody>
                        <Center>
                            <FaUser size="52px" />
                        </Center>
                        <br />
                        <Flex flexDir="column" fontSize="2xl">
                            <Flex justifyContent="start" alignItems="center" my="1">
                                <FaUserEdit size="30px" />
                                <Box mx="2">

                                    <Link to="/editpersonalinfo">Personal Details</Link>
                                </Box>
                            </Flex>
                            <Flex justifyContent="start" alignItems="center" my="1">

                                <FaCar size="30px" />
                                <Box mx="2">

                                    <Link>Vehicle</Link>
                                </Box>
                            </Flex>
                            <Flex justifyContent="start" alignItems="center" my="1">
                                <IoMdDocument size="30px" />
                                <Box mx="2">

                                    <Link>Documents</Link>
                                </Box>
                            </Flex>
                            <Flex justifyContent="start" alignItems="center" my="1">
                                <RiLogoutCircleRFill size="30px" />
                                <Box mx="2">

                                    <Link onClick={() => localStorage.removeItem("loggedInUserDetails")}>Log out</Link>
                                </Box>
                            </Flex>
                        </Flex>
                    </DrawerBody>

                </DrawerContent>
            </Drawer>
            <Flex w="90%" m="auto" justifyContent="space-between" alignItems="center" p="2">
                <Box>
                    <Link to="/">
                        <FaHome size="33px" />
                    </Link>
                </Box>
                <Box>
                    <Link to="/activity">
                        <FaHistory size="33px" />
                    </Link>
                </Box>
                <Box position="relative"><Link to="/notifications">
                    {/* <Box className='notification-number'><Text>5</Text>
                     </Box>  */}
                    <IoNotifications size="33px" />
                </Link>
                </Box>
                <Box><IoSettingsSharp size="33px" onClick={onOpen} /></Box>
            </Flex>
        </Box >
    )
}

export default BottomNavbar