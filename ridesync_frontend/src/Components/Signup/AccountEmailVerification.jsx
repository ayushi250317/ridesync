/* eslint-disable react-hooks/exhaustive-deps */
import { Box, Button, Center, Image, Text } from '@chakra-ui/react'
import React from 'react'
import { useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';

const AccountEmailVerification = () => {

    const location = useLocation();
    let email = location.state;
    useEffect(() => {
        if (!email) {
            window.history.go(-1);
        }
    }, [])

    const back = "<"

    return (
        <Center
            h="100vh"
            flexDir="column"
        >
            <Button onClick={() => window.history.go(-1)} position="absolute" top="8" left="8" backgroundColor="transparent" fontSize="2xl"> {back}  </Button>
            <Box width={["90%", "85%", "70%", "50%"]} m="auto">

                <Box className='flip-card-back' padding="5" px="7" backgroundColor="#4267B2" color="white" borderRadius="xl" boxShadow="2xl">
                    <Text textAlign="center" fontSize="3xl">Account email verification link sent.</Text>
                    <Box border="1px soild white">

                        <Image src='./check.gif' w="40%" m="auto"></Image>
                    </Box>
                    <Text textAlign="center" fontSize="xl">Please check your inbox {email.email} </Text>
                </Box>
                <Text textAlign="center" color='blue' mt="7">

                    <Link to="/login" >Go back to Login</Link>
                </Text>
            </Box>

        </Center>
    )
}

export default AccountEmailVerification