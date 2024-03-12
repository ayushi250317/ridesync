import { Box, Button, Center, Flex, Text, useMediaQuery } from '@chakra-ui/react'
import React, { useEffect, useState } from 'react'
import { IoChatboxEllipsesSharp } from "react-icons/io5";
import { FaCar } from "react-icons/fa";
import NotificationCard from './NotificationCard.';
import axios from 'axios';
import { API } from '../../sharedComponent/API';
import { Spinner } from '@chakra-ui/react'
const Notification = () => {
    const backbtn = "<"
    const [isLargerThan1280] = useMediaQuery("(min-width: 700px)");
    const [notificationsArray, setNotificationsArray] = useState([])
    const [loading, setLoading] = useState(false)
    useEffect(() => {
        setLoading(true)
        const loggedInUserInfo = JSON.parse(
            localStorage.getItem("loggedInUserDetails")
        );
        const config = {
            headers: { Authorization: `Bearer ${loggedInUserInfo.token}` },
        };
        axios.get(`${API}/notification/getNotifications`, config).then((resp) => {
            console.log("notification resp", resp.data);
            if (resp.data.success) {
                setNotificationsArray(resp.data.responseObject)

            }

        }).catch(err => {
            console.log("err", err);
        }).finally(() => {
            setLoading(false)
        })
    }, [])

    if (loading) return <Spinner size='xl' />
    return (
        <Box h="80vh">
            {!isLargerThan1280 &&
                <Button position="absolute" top="1" ml="2" backgroundColor="transparent" fontSize="2xl">   {backbtn} </Button>
            }

            <Text fontSize="3xl" my="3" textAlign="center" ml="5" fontWeight="medium">Notifications</Text>
            <Center borderRadius="2xl" w={["", "", "70%", "50%"]} m="auto" flexDir="column" mt={["", "", "5", "5"]} >
                <Box w="100%" overflow="auto" h={["90vh", "", "70vh", "70vh"]} mb={["10"]}>

                    <NotificationCard />
                </Box>


            </Center>

        </Box>
    )
}

export default Notification