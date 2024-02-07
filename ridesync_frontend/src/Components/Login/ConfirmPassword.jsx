import { Box, Button, Center, Flex, Image, Input, Text } from '@chakra-ui/react'
import React from 'react'
import { IoIosKey } from "react-icons/io";

const ConfirmPassword = () => {
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
                <Button colorScheme='facebook' w="100%">Submit</Button>
            </Box>
        </Center>
    )
}

export default ConfirmPassword