import { Box, Button, Center, Flex, Image, Input, Text } from '@chakra-ui/react'
import axios from 'axios';
import React from 'react'
import { useState } from 'react';
import { IoChevronBackSharp } from "react-icons/io5";
const ForgotPasswordEmail = () => {

    const [email, setEmail] = useState('')
    const [sentSuccess, setSentSuccess] = useState(false)

    const handleSubmitEmail = () => {
        axios.post('http://localhost:8073/api/v1/auth/forgotPassword', { email }).then(resp => {
            console.log(resp.data)
            if (resp.data?.success) {
                setSentSuccess(true)
            }
        }).catch(err => {
            console.log("error", err);
        })
    }

    const handleChange = (event) => {
        setEmail(event.target.value)

    }
    console.log("email", email);
    return (
        <Center h="100vh" className="flip-card">

            <Box border="1px solid lightgray" padding="5" px="7" borderRadius="xl" boxShadow="xl" w={["90%", "80%", "45%", "35%"]} className="flip-card-inner">
                {!sentSuccess ? <Box className="flip-card-front">

                    <IoChevronBackSharp />
                    <Text textAlign="center" fontSize="2xl" fontWeight="semibold">Reset Password</Text>
                    <Text fontSize="large" my="2">To reset your password, enter the email address you use to signin to Ridesync.</Text>
                    <br />
                    <Input placeholder='Enter you email' value={email} onChange={handleChange}></Input>

                    <Button w="100%" my="4" colorScheme='blue' onClick={handleSubmitEmail}>Submit</Button>
                </Box> :
                    <Box className='flip-card-back' padding="5" px="7" backgroundColor="#4267B2" color="white" borderRadius="xl" boxShadow="2xl">
                        <Text textAlign="center" fontSize="3xl">Password link sent.</Text>
                        <Box border="1px soild white">

                            <Image src='./check.gif' w="40%" m="auto"></Image>
                        </Box>
                        <Text textAlign="center" fontSize="xl">Please check your inbox {email}</Text>
                    </Box>}

            </Box>
        </Center>
    )
}

export default ForgotPasswordEmail