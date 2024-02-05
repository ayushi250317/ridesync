import { Box, Button, Flex, Image, Input, Text } from '@chakra-ui/react'
import React from 'react'
import { Link } from 'react-router-dom'

const Signup = () => {
    return (
        <Box>
            <br />
            <Flex w={["100%", "95%", "90%", "80%"]} h={["", "", "100vh", "100vh"]} m="auto" justifyContent="center" align="center" flexDir={["column-reverse", "column-reverse", "row", "row"]}>

                <Flex w={["90%", "95%", "60%", "55%"]} justifyContent="center" flexDir="column" >

                    <Text fontWeight="medium" fontSize="3xl" textAlign="center">Create account</Text>
                    <Text mb="1">Name</Text>
                    <Input w={["100%", "100%", "90%", "90%"]} placeholder='Enter your name' type='text'></Input>
                    <Text mt="3" mb="1">Email</Text>

                    <Input w={["100%", "100%", "90%", "90%"]} placeholder='Enter your email' type='email'></Input>
                    <Text mt="3" mb="1">Address</Text>
                    <Input w={["100%", "100%", "90%", "90%"]} placeholder='Enter your address' type='text'></Input>
                    <Text mt="3" mb="1">Date of birth</Text>

                    <Input w={["100%", "100%", "90%", "90%"]} placeholder='Date of Birth' type='date'></Input>
                    <Text mt="3" mb="1">Phone number</Text>
                    <Input w={["100%", "100%", "90%", "90%"]} placeholder='Enter your phone number' type='number'></Input>
                    <Text mt="3" mb="1">Password</Text>
                    <Input w={["100%", "100%", "90%", "90%"]} placeholder='Enter your password' type='password'></Input>
                    <br />
                    <Button w={["100%", "100%", "90%", "90%"]} colorScheme='blue'>Submit</Button>
                    <br />
                    <span >Already have an account? <span className='text-blue-700'><Link to="/login">Click here</Link></span></span>
                </Flex>
                <Flex w="45%" >
                    <Image src='/images.png' w={["100%", "100%", "85%", "80%"]}></Image>
                </Flex>
            </Flex>
        </Box>
    )
}

export default Signup