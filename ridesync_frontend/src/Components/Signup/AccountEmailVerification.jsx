import { Box, Center, Image, Text } from '@chakra-ui/react'
import axios from 'axios';
import React from 'react'
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { API } from '../../sharedComponent/API';

const AccountEmailVerification = () => {

    const location = useLocation();
    let email = location.state;
    useEffect(() => {
        if (!email) {
            window.history.go(-1);
        }
    }, [])


    console.log("email ", email);

    return (
        <Center
            h="100vh"
        > <Box width={["90%", "85%", "70%", "50%"]} m="auto" className='flip-card-back' padding="5" px="7" backgroundColor="#4267B2" color="white" borderRadius="xl" boxShadow="2xl">
                <Text textAlign="center" fontSize="3xl">Account email verification link sent.</Text>
                <Box border="1px soild white">

                    <Image src='./check.gif' w="40%" m="auto"></Image>
                </Box>
                <Text textAlign="center" fontSize="xl">Please check your inbox {email.email} </Text>
            </Box></Center>
    )
}

export default AccountEmailVerification