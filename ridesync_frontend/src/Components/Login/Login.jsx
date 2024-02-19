import { Box, Button, Flex, Image, Input, Text } from '@chakra-ui/react'
import React from 'react'
import { Link } from 'react-router-dom'

const Login = () => {
    return (
        <Box  >
            <Flex w={["100%", "95%", "90%", "80%"]} h={["", "", "100vh", "100vh"]} m="auto" justifyContent="center" align="center" flexDir={["column", "column", "row", "row"]}>
                <Flex w="50%" borderRight={["", "", "1px solid lightgray", "1px solid lightgray"]}>
                    <Image src='/try2.png' w={["100%", "100%", "85%", "80%"]}></Image>
                </Flex>
                <Flex w={["100%", "95%", "60%", "50%"]} justifyContent="center" align="center" flexDir="column" >
                    <Text fontWeight="medium" fontSize="3xl">Login</Text>
                    <br />
                    <Input w="75%" placeholder='email'></Input>
                    <br />
                    <Input w="75%" placeholder='password'></Input>
                    <br />
                    <Button w="75%" colorScheme='blue'>Submit</Button>
                    <br />
                    <span >Haven't registered yet ? <span className='text-blue-700'><Link to="/signup">Click here</Link></span></span>
                </Flex>
            </Flex>
        </Box>
    )
}

export default Login