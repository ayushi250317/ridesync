import { Box, Button, Center, Flex, Image, Input, Text } from '@chakra-ui/react'
import axios from 'axios';
import React from 'react'
import { useEffect } from 'react';
import { IoIosKey } from "react-icons/io";
import { useParams } from 'react-router-dom';
import { API } from '../../sharedComponent/API';
import { useState } from 'react';

const ConfirmPassword = () => {

    const { token, userId } = useParams();
    const [isTrue, setIsTrue] = useState(false);
    // console.log({ params });
    useEffect(() => {
        axios.get(`${API}/auth/resetPassword?token=${token}&id=${userId}`).then(resp => {
            console.log("resp of forget pass", resp.data);
            if (resp.data.success) {
                setIsTrue(true)
            }
        }).catch(err => {
            console.log("err in confirm password", err.message);
        })
    }, [])


    // axios.get(`${API}/`)
    if (!isTrue) return <><Text>Unauthorized</Text></>

    return (
        <Center h="100vh">
            <Box w={["90%", "70%", "60%", "40%"]} m="auto" border="1px solid lightgray" borderRadius="xl" boxShadow="2xl" p="10">
                <Center>
                    <IoIosKey size="55px" />
                </Center>
                <Text textAlign="center" fontSize={["2xl", "2xl", "3xl", "3xl"]}>Forgot Password?</Text>
                <br />
                <Text>Enter a new Password</Text>
                <Input placeholder='password' type='password'></Input>
                <br />
                <br />
                <Text>Re-type new Password</Text>
                <Input placeholder='confirm password' type='password'></Input>
                <br />
                <br />
                <Button colorScheme='facebook' w="100%" >Submit</Button>
            </Box>
        </Center>
    )
}

export default ConfirmPassword