import { Box, Button, Center, Text, useMediaQuery } from '@chakra-ui/react'
import React, { useEffect, useState } from 'react'
import NotificationCard from './NotificationCard.';
import axios from 'axios';
import { API } from '../../sharedComponent/API';
import { Spinner } from '@chakra-ui/react'

const Notification = () => {
    const backbtn = "<"
    const [isLargerThan1280] = useMediaQuery("(min-width: 700px)");
    const [notificationsArray, setNotificationsArray] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true)
        const loggedInUserInfo = JSON.parse(
            localStorage.getItem("loggedInUserDetails")
        );
        const config = {
            headers: { Authorization: `Bearer ${loggedInUserInfo.token}` },
        };
        axios.get(`${API}/notification/getNotifications`, config).then((resp) => {
            if (resp.data.success) {
                setNotificationsArray(resp.data.responseObject)

            }

        }).catch(err => {
            console.log("err", err);
        }).finally(() => {
            setLoading(false)
        })
    }, [])

    if (loading) return <Center h="80vh">

        <Spinner size='xl' />
    </Center>
    return (
        <Box h="80vh">
            {!isLargerThan1280 &&
                <Button position="absolute" top="1" ml="2" backgroundColor="transparent" fontSize="2xl"
                    onClick={() => window.history.go(-1)}>   {backbtn} </Button>
            }

            <Text fontSize="3xl" my="3" textAlign="center" ml="5" fontWeight="medium">Notifications</Text>
            <Center borderRadius="2xl" w={["", "", "70%", "50%"]} m="auto" flexDir="column" mt={["", "", "5", "5"]} >
                <Box w="100%" overflow="auto" h={["90vh", "", "70vh", "70vh"]} mb={["10"]}>
                    {
                        notificationsArray.length ? (
                            notificationsArray.map(elem => {
                                return <NotificationCard cardAttributes={elem}/>
                            })
                        ) : (
                            <Text textAlign="center">No notifications available</Text>
                        )
                    }
                </Box>


            </Center>
        </Box>
    )
}

export default Notification